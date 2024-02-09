package csit321.cloudcrypt.Controller.Shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Service.User.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/LogIn")
public class LogInAccountController {

        private final UserAccountService userAccountService;
        private final ObjectMapper objectMapper;

        @Autowired
        public LogInAccountController(UserAccountService userAccountService) {
            this.userAccountService = userAccountService;
            this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }

        @PostMapping(path = "/Validate")
        public ResponseEntity<String> logInAccount(@RequestBody String json) {
            try {
                JsonNode jsonNode = objectMapper.readTree(json);
                //  todo: String role = jsonNode.get("SelectUser").asText();
                String username = jsonNode.get("username").asText();
                String password = jsonNode.get("password").asText();
                String result = userAccountService.logInAccount(username, password);
                if (result.matches("Success")) {
                    return new ResponseEntity<>("Success",HttpStatus.OK);
                }
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>("Error Exc", HttpStatus.BAD_REQUEST);
            }
        }
}
