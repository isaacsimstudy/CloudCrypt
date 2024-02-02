package csit321.cloudcrypt.Controller.Shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserProfile;
import csit321.cloudcrypt.Service.User.UserProfileService;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Account")
public class CreateAccountController {

    private final UserProfileService userProfileService;
    private final UserAccountService userAccountService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CreateAccountController(UserAccountService userAccountService, UserProfileService userProfileService) {
        this.userAccountService = userAccountService;
        this.userProfileService = userProfileService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/Create")
    public ResponseEntity<String> createAccount(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();
            String title = jsonNode.get("title").asText();
            String email = jsonNode.get("email").asText();
            String firstName = jsonNode.get("firstName").asText();
            String lastName = jsonNode.get("lastName").asText();
            String dateOfBirth = jsonNode.get("dateOfBirth").asText();
            String address = jsonNode.get("address").asText();
            String phoneNumber = jsonNode.get("phoneNumber").asText();
            if (userAccountService.readAccount(username) != null) {
                return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
            }
            if (userAccountService.readAccount(email) != null) {
                return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
            }
            if (userProfileService.readUserProfile(title) == null) {
                return new ResponseEntity<>("Title does not exist", HttpStatus.BAD_REQUEST);
            }
            if (username == null || password == null || title == null || email == null || firstName == null || lastName == null || address == null || phoneNumber == null) {
                return new ResponseEntity<>("Missing fields", HttpStatus.BAD_REQUEST);
            }
            userAccountService.createAccount(username, password, title, email, firstName, lastName, address, phoneNumber, dateOfBirth);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
