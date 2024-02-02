package csit321.cloudcrypt.Controller.Owner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import csit321.cloudcrypt.Service.User.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Profile")
public class CreateUserProfileController {
    private final UserProfileService userProfileService;

    private final ObjectMapper objectMapper;

    @Autowired
    public CreateUserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PostMapping(path = "/Create")
    public ResponseEntity<String> createProfile(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String privilege = jsonNode.get("privilege").asText();
            String title = jsonNode.get("title").asText();
            if (!title.matches("^[a-zA-Z]+$")) {
                return new ResponseEntity<>("Invalid title: Title must contain only letters and spaces", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            if (!privilege.matches("^[a-zA-Z]+$")) {
                return new ResponseEntity<>("Invalid privilege: Privilege must contain only letters and spaces", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            if (userProfileService.readUserProfile(title) != null) {
                return new ResponseEntity<>("Invalid title: Title already exists", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            userProfileService.createUserProfile(privilege, title);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
