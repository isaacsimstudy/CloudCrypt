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
@RequestMapping(path = "/Profile")
public class ReadUserProfileController {
    private final UserProfileService userProfileService;

    private final ObjectMapper objectMapper;

    @Autowired
    public ReadUserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @GetMapping(path = "/Read/{param}")
    public ResponseEntity<String> readProfile(@PathVariable String param){
        try {
            // if param has numbers then invalid
            if (param.matches(".*\\d.*")) {
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userProfileService.readUserProfile(param).toString(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
