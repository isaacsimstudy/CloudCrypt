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

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/SecurityPolicy")
public class DeleteSecurityPolicyController {

    private final SecurityPolicyServiceImpl securityPolicyServiceImpl;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public DeleteSecurityPolicyController(SecurityPolicyServiceImpl securityPolicyServiceImpl,
                                        SecurityPolicyRepository securityPolicyRepository,
                                        UserAccountRepository userAccountRepository) {
        this.securityPolicyServiceImpl = securityPolicyServiceImpl;
        this.userAccountRepository = userAccountRepository;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    //Used only for deleting all policies in an account during account deletion
    @DeleteMapping(path = "/Delete")
    public ResponseEntity<String> deleteSecurityPolicy(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String securityPolicyId = jsonNode.get("Id").asText();
            String result = securityPolicyServiceImpl.deleteSecurityPolicy(java.util.UUID.fromString(securityPolicyId));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
