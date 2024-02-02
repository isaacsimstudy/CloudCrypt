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
public class ReadAccountController {

    private final UserProfileService userProfileService;
    private final UserAccountService userAccountService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReadAccountController(UserAccountService userAccountService, UserProfileService userProfileService) {
        this.userAccountService = userAccountService;
        this.userProfileService = userProfileService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @GetMapping(path = "/Read/{username}")
    public ResponseEntity<String> readAccount(@PathVariable String param) {
        try {
            if (param.matches(".*\\d.*")) {
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userAccountService.readAccount(param).toString(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
