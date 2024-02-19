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

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

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
                Map<String, String> updates = new HashMap<>();
                updates = convertStringToMap(json);
                String result = securityPolicyServiceImpl.updateSecurityPolicy( id ,updates);
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
