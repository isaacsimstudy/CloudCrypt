package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.Base64;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class DownloadKeyController {
    private final KeyService keyService;
    private final KeyRepository keyRepository;

    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

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
        content.append("Password Hash: ").append(decodeFromBase64(key.getPassword_hash())).append("\n");
        return content.toString();
    }

    public static String decodeFromBase64(String password_hash){
        try {
            // Decode the Base64 encoded string
            byte[] decodedBytes = Base64.getDecoder().decode(password_hash);

            // Convert the decoded bytes to a string
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            // If decoding fails, return the original password hash
            return password_hash;
        }
    }


}
