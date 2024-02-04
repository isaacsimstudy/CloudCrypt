package csit321.cloudcrypt.Controller.Shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Service.User.UserAccountService;
import csit321.cloudcrypt.Service.User.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Account")
public class SuspendAccountController {

        private final UserProfileService userProfileService;
        private final UserAccountService userAccountService;
        private final ObjectMapper objectMapper;

        @Autowired
        public SuspendAccountController(UserAccountService userAccountService, UserProfileService userProfileService) {
            this.userAccountService = userAccountService;
            this.userProfileService = userProfileService;
            this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }

        @PostMapping(path = "/Suspend")
        public ResponseEntity<String> suspendAccount(@RequestBody String json) {
            try {
                JsonNode jsonNode = objectMapper.readTree(json);
                String username = jsonNode.get("username").asText();
                if (userAccountService.readAccount(username) == null) {
                    return new ResponseEntity<>("Username does not exist", HttpStatus.BAD_REQUEST);
                }
                userAccountService.suspendAccount(username);
                return new ResponseEntity<>("Success",HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
            }
        }
}
