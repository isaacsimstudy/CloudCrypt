package csit321.cloudcrypt.Controller.Shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import csit321.cloudcrypt.Service.User.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
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
            userProfileService.createProfile(privilege, title);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
