package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.KeyService;
import csit321.cloudcrypt.Service.User.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.FileInputStream;
import java.io.IOException;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")

public class GenerateKeyController {
    private static final Logger LOGGER = Logger.getLogger(DownloadKeyController.class.getName());
    private final KeyService keyService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    private static final String SECRET_KEY = "the_secret_key";
    private static final String SALT = "the_salt";
    @Autowired
    public GenerateKeyController(KeyService keyService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.keyService = keyService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/GenerateKey")
    public ResponseEntity<String> generateKey(@RequestBody String json) {
        try {
            LOGGER.info("Received request to generate key");
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            LOGGER.info("Username: " + username);
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            LOGGER.info("User account found: " + userAccount.getId());
            try {
                String name = jsonNode.get("name").asText();
                String password_hash = jsonNode.get("password_hash").asText();

                LOGGER.info("Name: " + name);
                LOGGER.info("Password hash: " + password_hash);

                String Key = keyService.generateKey(userAccount,
                                name,
                        password_hash
                                );
                LOGGER.info("Key generated: " + Key);
                return new ResponseEntity<>(Key, HttpStatus.OK);
            }
            catch (Exception e) {
                LOGGER.warning("Invalid input: " + e.getMessage());
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            LOGGER.severe("Error generating key: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path = "/GenerateRandomKey")
    public ResponseEntity<String> genRandomKey() {

        String key = generateRandomKey();

        return new ResponseEntity<>(key, HttpStatus.OK);

    }
    private String generateRandomKey(){
        try {
            // Generate random bytes
            byte[] randomBytes = new byte[32];
            new SecureRandom().nextBytes(randomBytes);

            LOGGER.info("Generated random bytes: " + Arrays.toString(randomBytes));

            byte[] encryptedBytes = encrypt(randomBytes);
            LOGGER.info("Encrypted bytes: " + Arrays.toString(encryptedBytes));


            // Encode bytes to base64
            String base64Key = Base64.getEncoder().encodeToString(encryptedBytes);
            LOGGER.info("Base64 encoded key: " + base64Key);


            // Truncate to fit into VARCHAR(72) column
            String truncatedKey = base64Key.substring(0, Math.min(base64Key.length(), 72));
            LOGGER.info("Truncated key: " + truncatedKey);

            // Log key length
            LOGGER.info("Key length: " + truncatedKey.length());

            return truncatedKey;
        } catch (Exception e) {
            LOGGER.severe("Error generating random key: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private byte[] encrypt(byte[] input) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(input);

            // Log the length of the encrypted bytes
            LOGGER.info("Length of encrypted bytes: " + encryptedBytes.length);

            return encryptedBytes;
        } catch (Exception e) {
            LOGGER.severe("Error encrypting bytes: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
