package csit321.cloudcrypt.Controller.GCM;

import com.fasterxml.jackson.databind.ObjectMapper;
import csit321.cloudcrypt.Controller.Customer.DownloadKeyController;
import jakarta.persistence.Table;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Repository.KeyRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.logging.*;
import java.util.UUID;
import java.nio.ByteBuffer;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
@Table(name = "key")
public class FileDecryptionController {

    private static final Logger LOGGER = Logger.getLogger(DownloadKeyController.class.getName());
    private final KeyService keyService;
    private final KeyRepository keyRepository;

    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    private static final String SECRET_KEY = "the_secret_key";
    private static final String SALT = "the_salt";

    @Autowired
    public FileDecryptionController(KeyService keyService,
                                    KeyRepository keyRepository,
                                    UserAccountRepository userAccountRepository) {
        this.keyService = keyService;
        this.keyRepository = keyRepository;
        this.userAccountRepository = userAccountRepository;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/DecryptFiles")
    public ResponseEntity<byte[]> decryptFiles(@RequestParam("file") MultipartFile file) {
        try {
            LOGGER.info("Received request to Decrypt " );
            LOGGER.info("File Name: " + file.getOriginalFilename());
            //get file bytes
            byte[] fileBytes = getFileBytes(file);

            byte[] uuidBytes = extractUUIDBytes(file);

            //read first 16 byte(the uuid)

            UUID uuid = bytesToUUID2(uuidBytes);
            //byte[] uuidBytes = Arrays.copyOfRange(fileBytes, 0, 16);
            //UUID uuid = UUID.nameUUIDFromBytes(bytesToUUID(uuidBytes));
            LOGGER.info("UUID extracted from file: " + uuid);

            //get Encryption Key
            Key key = keyRepository.findKeyById(uuid);
            LOGGER.info("Encryption Key: " + key);

            if (key == null) {
                // Convert error message to byte array
                byte[] errorMessageBytes = "Key not found.".getBytes();
                return new ResponseEntity<>(errorMessageBytes, HttpStatus.BAD_REQUEST);
            }
            //7b222e74-6167-223a-2266-696c65222c22
            //3401232f-4fce-4134-9f76-013198107d92

            // Remove the UUID bytes from the encrypted data

            byte[] encryptedFileData = Arrays.copyOfRange(fileBytes, 16, fileBytes.length);
            LOGGER.info("File Bytes: " + Arrays.toString(encryptedFileData));


            // Decode password hash to obtain original key
            byte[] decodedKeyBytes = Base64.getDecoder().decode(key.getPassword_hash());
            // Log the length of the decrypted key
            LOGGER.info("decodedKeyBytes: " + decodedKeyBytes.length);


            // Ensure that the decrypted key has the expected length for AES encryption
            if (decodedKeyBytes.length != 16 && decodedKeyBytes.length != 24 && decodedKeyBytes.length != 32) {
                LOGGER.warning("Decoded key length does not match expected AES key lengths (16, 24, or 32 bytes).");
                // Handle the error or throw an exception if the key length is incorrect
            }

            // Decrypt AES key
            //byte[] decryptedKeyBytes = decrypt(decodedKeyBytes);
            //LOGGER.info("Decrypted key length: " + decryptedKeyBytes.length);

            // Ensure that the decrypted key has the expected length for AES encryption
            //if (decryptedKeyBytes.length != 16 && decryptedKeyBytes.length != 24 && decryptedKeyBytes.length != 32) {
                //LOGGER.warning("Decrypted key length does not match expected AES key lengths (16, 24, or 32 bytes).");
                // Handle the error or throw an exception if the key length is incorrect
            //}


            // Fixed IV
            byte[] fixedIV = "123456789!!!!!!!".getBytes();
            LOGGER.info("Fixed IV: " + Arrays.toString(fixedIV));

            byte[] decryptedFile = decryptFile(file,  decodedKeyBytes, fixedIV);
            LOGGER.info("Decryption Success");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + "_encrypted\"")
                    .body(decryptedFile);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decrypting files", e);
            // Convert error message to byte array
            byte[] errorMessageBytes = e.getMessage().getBytes();
            return new ResponseEntity<>(errorMessageBytes, HttpStatus.BAD_REQUEST);
        }
    }


    public static byte[] decryptFile(MultipartFile file, byte[] secretKeyBytes, byte[] fixedIV) {
        LOGGER.info("Decrypting File: " + file.getOriginalFilename());
        LOGGER.info("Secret Key: " + Arrays.toString(secretKeyBytes));
        LOGGER.info("Fixed IV: " + Arrays.toString(fixedIV));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (InputStream fis = file.getInputStream()) {
            // Initialize cipher for decryption
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, fixedIV);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);

            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] decryptedChunk = cipher.update(buffer, 0, bytesRead);
                baos.write(decryptedChunk);
            }

            // Write the final decrypted chunk
            byte[] decryptedFinalChunk = cipher.doFinal();
            baos.write(decryptedFinalChunk);

            LOGGER.info("Authentication Tag: " + Arrays.toString(cipher.getParameters().getParameterSpec(GCMParameterSpec.class).getIV()));

            LOGGER.info("Decryption Success");
        } catch (AEADBadTagException e) {
            LOGGER.log(Level.SEVERE, "Decryption failed due to AEADBadTagException", e);
            throw new RuntimeException("Decryption failed: Invalid authentication tag. The encrypted data may have been tampered with.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decrypting file", e);
            throw new RuntimeException("Decryption failed: " + e.getMessage());
        }

        return baos.toByteArray();
    }

    //For large files convert to bytes
    public byte[] getFileBytes(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192]; // 8KB buffer (adjust as needed)
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }

    //convert byte to uuid
    // Method to retrieve bytes from a byte array and log in hexadecimal format
    private static byte[] bytesToUUID(byte[] fileBytes) {
        byte[] uuidBytes = Arrays.copyOfRange(fileBytes, 0, 16);

        // Log the bytes in hexadecimal format
        StringBuilder hexString = new StringBuilder();
        for (byte b : uuidBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        LOGGER.info("Retrieved Bytes (Hexadecimal): " + hexString.toString());

        return uuidBytes;
    }

    public static byte[] extractUUIDBytes(MultipartFile file) {
        try (InputStream fis = file.getInputStream()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Read the first 16 bytes from the file
            byte[] buffer = new byte[16];
            int bytesRead = fis.read(buffer);

            // If the file has less than 16 bytes, return null or handle accordingly
            if (bytesRead < 16) {
                return null; // Or handle accordingly
            }

            // Close the stream
            fis.close();

            // Return the first 16 bytes as UUID bytes
            return buffer;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error extracting UUID bytes from file: " + file.getOriginalFilename(), e);
            return null;
        }
    }

    public static UUID bytesToUUID2(byte[] uuidBytes) {
        // Wrap the byte array with ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(uuidBytes);

        // Retrieve the most significant bits and least significant bits
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();

        // Create and return the UUID
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    private byte[] decrypt(byte[] input) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        LOGGER.info("Decrypting bytes method");
        LOGGER.info("Input bytes: " + Arrays.toString(input));

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        LOGGER.info("Decrypted bytes: " + Arrays.toString(input));
        return cipher.doFinal(input);

    }

}
