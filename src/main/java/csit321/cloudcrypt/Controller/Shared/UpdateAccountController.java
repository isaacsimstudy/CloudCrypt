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
public class UpdateAccountController {

    private final UserProfileService userProfileService;
    private final UserAccountService userAccountService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UpdateAccountController(UserAccountService userAccountService, UserProfileService userProfileService) {
        this.userAccountService = userAccountService;
        this.userProfileService = userProfileService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/Update")
    public ResponseEntity<String> updateAccount(@RequestBody String json) {
        try {
            // We can confirm behavior of data passing later on.
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
            if (userAccountService.readAccount(username) == null) {
                return new ResponseEntity<>("Username does not exist", HttpStatus.BAD_REQUEST);
            }
            if (userProfileService.readUserProfile(title) == null) {
                return new ResponseEntity<>("Title does not exist", HttpStatus.BAD_REQUEST);
            }
            userAccountService.updateAccount(username, password, title, email, firstName, lastName, address, phoneNumber, dateOfBirth);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }
}
