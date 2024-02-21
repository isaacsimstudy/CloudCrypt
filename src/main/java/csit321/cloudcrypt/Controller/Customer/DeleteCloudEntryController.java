package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.KeyRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class DeleteCloudEntryController {
    private final CloudService cloudService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;
    private final KeyRepository keyRepository;

    @Autowired
    public DeleteCloudEntryController(CloudService cloudService,
                                      UserAccountRepository userAccountRepository,
                                      KeyRepository keyRepository) {
        this.cloudService = cloudService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
        this.keyRepository = keyRepository;
    }

    @PostMapping(path = "/DeleteCloudEntry")
    public ResponseEntity<String> deleteCloudEntry(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            Map<String, String> param = new HashMap<>();
            param = convertStringToMap(json);
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElse(null);
            if (userAccount == null) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            String cloudEntry = cloudService.deleteCloud(param);
            return new ResponseEntity<>(cloudEntry, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    public Map<String, String> convertStringToMap(String str) {
        // Convert a string to a map
        Map<String, String> map = new HashMap<>();
        StringTokenizer st = new StringTokenizer(str, ",");
        while (st.hasMoreTokens()) {
            String[] pair = st.nextToken().split("=");
            map.put(pair[0], pair[1]);
        }
        return map;
    }
}
