package csit321.cloudcrypt.Controller.Shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserProfile;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Service.User.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/Account")
public class CreateAccountController {

    private final UserProfile userProfile;
    private final UserAccountService userAccountService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CreateAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        this.userProfile = new UserProfile();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/Create")
    public ResponseEntity<String> createAccount(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();
            String email = jsonNode.get("email").asText();
            String firstName = jsonNode.get("firstName").asText();
            String lastName = jsonNode.get("lastName").asText();
            String address = jsonNode.get("address").asText();
            String phoneNumber = jsonNode.get("phoneNumber").asText();
            userAccountService.createAccount(username, password, email, firstName, lastName, address, phoneNumber);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
