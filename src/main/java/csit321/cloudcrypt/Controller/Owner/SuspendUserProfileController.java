package csit321.cloudcrypt.Controller.Owner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Service.User.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Owner/Profile")
public class SuspendUserProfileController {

    private final UserProfileService userProfileService;

    private final ObjectMapper objectMapper;

    @Autowired
    public SuspendUserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @PutMapping(path = "/Suspend/{title}")
    public ResponseEntity<String> suspendProfile(@PathVariable String title) {
        try {
            if (!title.matches("^[a-zA-Z]+$")) {
                return new ResponseEntity<>("Invalid title: Title must contain only letters and spaces", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            userProfileService.suspendUserProfile(title);
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
