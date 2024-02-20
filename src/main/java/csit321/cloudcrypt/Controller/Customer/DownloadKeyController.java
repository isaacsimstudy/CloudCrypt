package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Repository.KeyRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class DownloadKeyController {
    private static final Logger LOGGER = Logger.getLogger(DownloadKeyController.class.getName());
    private final KeyService keyService;
    private final KeyRepository keyRepository;

    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    private static final String SECRET_KEY = "the_secret_key";
    private static final String SALT = "the_salt";

    @Autowired
    public DownloadKeyController(KeyService keyService,
                               KeyRepository keyRepository,
                               UserAccountRepository userAccountRepository) {
        this.keyService = keyService;
        this.keyRepository = keyRepository;
        this.userAccountRepository = userAccountRepository;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/DownloadKey")
    public ResponseEntity<String> downloadKey(@RequestBody String uuid){
        try {
            Key key = keyRepository.findKeyById(java.util.UUID.fromString(uuid));
            if (key == null) {
                return new ResponseEntity<>("Key not found.", HttpStatus.BAD_REQUEST);
            }
            String fileContent = getKeyFileContent(key);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=key_info.txt");
            return ResponseEntity.ok().headers(headers).body(fileContent);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private String getKeyFileContent(Key key) {
        StringBuilder content = new StringBuilder();
        content.append("Name: ").append(key.getName()).append("\n");
        content.append("Key ID: ").append(key.getId()).append("\n");
        content.append("Password Hash: ").append(key.getPassword_hash()).append("\n");
        return content.toString();
    }

    public String decodeFromBase64(String password_hash){
        try {
            LOGGER.info("Decoding Base64: " + password_hash);
            // Decode the Base64 encoded string
            byte[] decodedEncryptedBytes = Base64.getDecoder().decode(password_hash);

            // Decrypt the encrypted bytes
            byte[] decryptedBytes = decrypt(decodedEncryptedBytes);

            // Convert the decrypted bytes to a string
            String decryptedString = new String(decryptedBytes);
            LOGGER.info("Decryption successful: " + decryptedString);
            return decryptedString;

        } catch (IllegalArgumentException e) {
            // If decoding fails, return the original password hash
            LOGGER.warning("Error decoding Base64: " + e.getMessage());
            return password_hash;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error decoding Base64", e);
            throw new RuntimeException(e);
        }
    }
    private byte[] decrypt(byte[] input) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        LOGGER.info("Decrypting bytes method");
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

}
