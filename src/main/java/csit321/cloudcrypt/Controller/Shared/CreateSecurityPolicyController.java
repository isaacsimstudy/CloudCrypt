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

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/SecurityPolicy")
public class CreateSecurityPolicyController {

    private final SecurityPolicyServiceImpl securityPolicyServiceImpl;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public CreateSecurityPolicyController(SecurityPolicyServiceImpl securityPolicyServiceImpl,
                                          SecurityPolicyRepository securityPolicyRepository,
                                          UserAccountRepository userAccountRepository) {
        this.securityPolicyServiceImpl = securityPolicyServiceImpl;
        this.userAccountRepository = userAccountRepository;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/Create")
    public ResponseEntity<String> createSecurityPolicy(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            String policyName = jsonNode.get("policyName").asText();
            String description = jsonNode.get("description").asText();
            String enforcementLevel = jsonNode.get("enforcementLevel").asText();
            String policyType = jsonNode.get("policyType").asText();
            String status = jsonNode.get("status").asText();
            String parameter = jsonNode.get("parameters").asText();
            Map<String, String> parameters = convertStringToMap(parameter);

            String result = securityPolicyServiceImpl.createSecurityPolicy(userAccount, policyName, description, enforcementLevel, policyType, parameters, status);
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
            String token = st.nextToken();
            String[] keyValue = token.split(":");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

}
