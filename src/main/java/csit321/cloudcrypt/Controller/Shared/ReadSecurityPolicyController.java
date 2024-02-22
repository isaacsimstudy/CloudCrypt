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
public class ReadSecurityPolicyController {

        private final SecurityPolicyServiceImpl securityPolicyServiceImpl;
        private final ObjectMapper objectMapper;

        private final UserAccountRepository userAccountRepository;

        @Autowired
        public ReadSecurityPolicyController(SecurityPolicyServiceImpl securityPolicyServiceImpl,
                                        SecurityPolicyRepository securityPolicyRepository,
                                        UserAccountRepository userAccountRepository) {
            this.securityPolicyServiceImpl = securityPolicyServiceImpl;
            this.userAccountRepository = userAccountRepository;
            this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }

        @GetMapping(path = "/Read")
        public ResponseEntity<String> readSecurityPolicy() {
            try {
                String result = securityPolicyServiceImpl.getSecurityPolicy();
                return new ResponseEntity<>(result, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
}

