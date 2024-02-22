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
public class FileEncryptionController {

    private static final Logger LOGGER = Logger.getLogger(DownloadKeyController.class.getName());
    private final KeyService keyService;
    private final KeyRepository keyRepository;

    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    private static final String SECRET_KEY = "the_secret_key";
    private static final String SALT = "the_salt";

    @Autowired
    public FileEncryptionController(KeyService keyService,
                                 KeyRepository keyRepository,
                                 UserAccountRepository userAccountRepository) {
        this.keyService = keyService;
        this.keyRepository = keyRepository;
        this.userAccountRepository = userAccountRepository;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/EncryptFiles")
    public ResponseEntity<byte[]> encryptFiles(@RequestParam("keyId") UUID keyId, @RequestParam("file") MultipartFile file)  {
        try {
            LOGGER.info("Received request to encrypt file with key ID: " + keyId);
            LOGGER.info("File Name: " + file.getOriginalFilename());

            Key key = keyRepository.findKeyById(keyId);
            LOGGER.info("Key Found: " + keyId);

            if (key == null) {
                // Convert error message to byte array
                byte[] errorMessageBytes = "Key not found.".getBytes();
                return new ResponseEntity<>(errorMessageBytes, HttpStatus.BAD_REQUEST);
            }

            // Decode password hash to obtain original key
            LOGGER.info("Decoding Key ");
            byte[] decodedKeyBytes = Base64.getDecoder().decode(key.getPassword_hash());
            // Log the length of the decrypted key
            LOGGER.info("decodedKeyBytes: " + decodedKeyBytes.length);


            // Ensure that the decrypted key has the expected length for AES encryption
            if (decodedKeyBytes.length != 16 && decodedKeyBytes.length != 24 && decodedKeyBytes.length != 32) {
                LOGGER.warning("Decoded key length does not match expected AES key lengths (16, 24, or 32 bytes).");
                // Handle the error or throw an exception if the key length is incorrect
            }

            // Decrypt AES key
            byte[] decryptedKeyBytes = decrypt(decodedKeyBytes);

            // Log the length of the decrypted key
            LOGGER.info("Decrypted key length: " + decryptedKeyBytes.length);

            // Ensure that the decrypted key has the expected length for AES encryption
            if (decryptedKeyBytes.length != 16 && decryptedKeyBytes.length != 24 && decryptedKeyBytes.length != 32) {
                LOGGER.warning("Decrypted key length does not match expected AES key lengths (16, 24, or 32 bytes).");
                // Handle the error or throw an exception if the key length is incorrect
            }

            // Fixed IV
            byte[] fixedIV = "123456789!!!!!!!".getBytes();
            LOGGER.info("Fixed IV: " + Arrays.toString(fixedIV));

            byte[] encryptedFile = encryptFile(file, keyId,  decryptedKeyBytes, fixedIV);
            LOGGER.info("Encryption Success");

            // Return the encrypted file as a byte array in the response body
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + "_encrypted\"")
                    .body(encryptedFile);
        } catch (Exception e) {
            // Log the error
            LOGGER.log(Level.SEVERE, "Error encrypting files", e);
            // Convert error message to byte array
            byte[] errorMessageBytes = e.getMessage().getBytes();
            return new ResponseEntity<>(errorMessageBytes, HttpStatus.BAD_REQUEST);
        }
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

    public static byte[] encryptFile(MultipartFile file,UUID uuid, byte[] secretKeyBytes, byte[] fixedIV) {
        LOGGER.info("Encrypting File: " + file.getOriginalFilename());
        LOGGER.info("Secret Key: " + Arrays.toString(secretKeyBytes));
        LOGGER.info("Fixed IV: " + Arrays.toString(fixedIV));

        try (InputStream fis = file.getInputStream()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] keyHash = computeSHA256Hash(secretKeyBytes);

            // Convert UUID to byte array
            byte[] uuidBytes = uuidToBytes(uuid);

            // Prepend UUID bytes to the output
            baos.write(uuidBytes);
            baos.write(keyHash);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, fixedIV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);

            byte[] buffer = new byte[8192]; // 8KB buffer
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] encryptedChunk = cipher.update(buffer, 0, bytesRead);
                baos.write(encryptedChunk);
            }
            byte[] encryptedFinalChunk = cipher.doFinal();
            baos.write(encryptedFinalChunk);

            LOGGER.info("Encryption Completed Successfully");
            return baos.toByteArray();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error encrypting file: " + file.getOriginalFilename(), e);
            return null;
        }
    }

    private static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    // Compute SHA-256 hash of the input bytes
    private static byte[] computeSHA256Hash(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input);
    }
}
