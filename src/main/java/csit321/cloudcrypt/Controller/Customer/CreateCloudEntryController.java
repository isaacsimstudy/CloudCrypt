package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.Cloud;
import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.CloudRepository;
import csit321.cloudcrypt.Repository.KeyRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Repository.FileInfoRepository;
import csit321.cloudcrypt.Service.CloudService;
import csit321.cloudcrypt.Service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class CreateCloudEntryController {
    private final CloudService cloudService;

    private final CloudRepository cloudRepository;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;
    private final KeyRepository keyRepository;

    private final FileInfoService fileInfoService;


    @Autowired
    public CreateCloudEntryController(CloudService cloudService, CloudRepository cloudRepository,
                                      UserAccountRepository userAccountRepository,
                                      KeyRepository keyRepository, FileInfoService fileInfoService) {
        this.cloudService = cloudService;
        this.cloudRepository = cloudRepository;
        this.fileInfoService = fileInfoService;
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
                                                        jsonNode.get("status").asText());

            Cloud cloud = cloudRepository.findCloudByFileName(jsonNode.get("cloudName").asText()).orElse(null);
            String fileInfo = fileInfoService.createFileInfo(cloud, jsonNode.get("originalSize").asLong(),
                                                              jsonNode.get("fileType").asText(),
                                                              jsonNode.get("originalHash").asText(),
                                                              jsonNode.get("encryptionType").asText(),
                                                              userAccount,
                                                              jsonNode.get("tags").asText());
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
