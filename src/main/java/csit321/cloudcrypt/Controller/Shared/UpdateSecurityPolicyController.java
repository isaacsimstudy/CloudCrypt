package csit321.cloudcrypt.Controller.Shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.SecurityPolicyRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.implementation.SecurityPolicyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/SecurityPolicy")
public class UpdateSecurityPolicyController {

        private final SecurityPolicyServiceImpl securityPolicyServiceImpl;
        private final ObjectMapper objectMapper;

        private final UserAccountRepository userAccountRepository;

        @Autowired
        public UpdateSecurityPolicyController(SecurityPolicyServiceImpl securityPolicyServiceImpl,
                                        SecurityPolicyRepository securityPolicyRepository,
                                        UserAccountRepository userAccountRepository) {
            this.securityPolicyServiceImpl = securityPolicyServiceImpl;
            this.userAccountRepository = userAccountRepository;
            this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }

        @PostMapping(path = "/Update")
        public ResponseEntity<String> updateSecurityPolicy(@RequestBody String json) {
            try {
                JsonNode jsonNode = objectMapper.readTree(json);
                UUID id = UUID.fromString(jsonNode.get("id").asText());
                Map<String, String> updates = convertStringToMap(json);
                String result = securityPolicyServiceImpl.updateSecurityPolicy( id ,updates);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

    public static Map<String, String> convertStringToMap(String jsonStr) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonStr);
        Map<String, String> map = new HashMap<>();

        // Iterating through the root fields of the JSON object
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            String key = field.getKey();
            JsonNode value = field.getValue();

            // Handling the 'parameters' field specifically
            if ("parameters".equals(key) && value.isObject()) {
                StringJoiner sj = new StringJoiner(",");
                value.fields().forEachRemaining(e -> sj.add(e.getKey() + ":" + e.getValue().asText()));
                map.put(key, sj.toString());
            } else {
                // For other fields, directly put their string value
                map.put(key, value.asText());
            }
        }

        return map;
    }
}
