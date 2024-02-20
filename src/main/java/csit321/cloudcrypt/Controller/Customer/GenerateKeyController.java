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
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")

public class GenerateKeyController {
    private final KeyService keyService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;
    @Autowired
    public GenerateKeyController(KeyService keyService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.keyService = keyService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/GenerateKey")
    public ResponseEntity<String> generateKey(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            try {
                String name = jsonNode.get("name").asText();
                String password_hash = jsonNode.get("password_hash").asText();

                String Key = keyService.generateKey(userAccount,
                                name,
                        password_hash
                                );
                return new ResponseEntity<>(Key, HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path = "/GenerateRandomKey")
    public ResponseEntity<String> genRandomKey() {

        String key = generateRandomKey();
        return new ResponseEntity<>(key, HttpStatus.OK);

    }
    private String generateRandomKey(){
        // Generate random bytes
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);

        // Encode bytes to base64
        String base64Key = Base64.getEncoder().encodeToString(randomBytes);

        // Truncate to fit into VARCHAR(72) column
        return base64Key.substring(0, Math.min(base64Key.length(), 72));
    }
}
