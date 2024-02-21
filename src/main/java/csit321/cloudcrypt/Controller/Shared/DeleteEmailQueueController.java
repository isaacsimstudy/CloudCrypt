package csit321.cloudcrypt.Controller.Shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.EmailQueueRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.implementation.EmailQueueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/EmailQueue")
public class DeleteEmailQueueController {

    private final EmailQueueServiceImpl emailQueueServiceImpl;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public DeleteEmailQueueController(EmailQueueServiceImpl emailQueueServiceImpl,
                                    EmailQueueRepository emailQueueRepository,
                                    UserAccountRepository userAccountRepository) {
        this.emailQueueServiceImpl = emailQueueServiceImpl;
        this.userAccountRepository = userAccountRepository;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/Delete")
    public ResponseEntity<String> deleteEmailQueue(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            Map<String, String> updates = convertStringToMap(json);
            String result = emailQueueServiceImpl.deleteEmailQueue(updates);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public Map<String, String> convertStringToMap(String str) {
        // Convert a string to a map
        Map<String, String> map = new HashMap<>();
        StringTokenizer st = new StringTokenizer(str, ",");
        while (st.hasMoreTokens()) {
            String[] keyValue = st.nextToken().split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }
}
