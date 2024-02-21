package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.KeyRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class CreateCloudEntryController {
    private final CloudService cloudService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;
    private final KeyRepository keyRepository;

    @Autowired
    public CreateCloudEntryController(CloudService cloudService,
                                      UserAccountRepository userAccountRepository,
                                      KeyRepository keyRepository) {
        this.cloudService = cloudService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
        this.keyRepository = keyRepository;
    }

    @PostMapping(path = "/CreateCloudEntry")
    public ResponseEntity<String> createCloudEntry(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            Key key = keyRepository.findKeyByName(jsonNode.get("keyName").asText());
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            String cloudEntry = cloudService.createCloud(userAccount,
                                                        jsonNode.get("cloudName").asText(),
                                                        jsonNode.get("cloudFilePath").asText(),
                                                        key,
                                                        jsonNode.get("cloudPath").asText(),
                                                        jsonNode.get("status").asText());
            return new ResponseEntity<>(cloudEntry, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
