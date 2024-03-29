package csit321.cloudcrypt.Controller.Shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Controller.Customer.CreateCustomerDetailController;
import csit321.cloudcrypt.Controller.Customer.CreateLoginSettingController;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Repository.UserProfileRepository;
import csit321.cloudcrypt.Service.User.UserAccountService;
import csit321.cloudcrypt.Service.User.UserProfileService;
import csit321.cloudcrypt.Controller.Customer.CreateNotificationSettingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Account")
public class CreateAccountController {

    private final UserProfileService userProfileService;
    private final UserAccountService userAccountService;
    private final ObjectMapper objectMapper;
    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreateCustomerDetailController createCustomerDetailController;
    private final CreateLoginSettingController createLoginSettingController;
    private final CreateNotificationSettingController createNotificationSettingController;

    @Autowired
    public CreateAccountController(UserAccountService userAccountService, UserProfileService userProfileService,
                                   UserAccountRepository userAccountRepository,
                                   UserProfileRepository userProfileRepository,
                                   CreateCustomerDetailController createCustomerDetailController,
                                   CreateLoginSettingController createLoginSettingController, CreateNotificationSettingController createNotificationSettingController) {
        this.userAccountService = userAccountService;
        this.userProfileService = userProfileService;
        this.createCustomerDetailController = createCustomerDetailController;
        this.createLoginSettingController = createLoginSettingController;
        this.createNotificationSettingController = createNotificationSettingController;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
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
            if (userAccountRepository.findUserAccountByUsername(username).orElse(null) != null) {
                return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
            }
            if (userAccountRepository.findUserAccountByEmail(email).orElse(null) != null) {
                return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
            }
            if (userProfileRepository.findUserProfileByTitle(title).orElse(null) == null) {
                return new ResponseEntity<>("Title does not exist", HttpStatus.BAD_REQUEST);
            }
            if (username == null || password == null || title == null || email == null || firstName == null || lastName == null || address == null || phoneNumber == null) {
                return new ResponseEntity<>("Missing fields", HttpStatus.BAD_REQUEST);
            }
            userAccountService.createAccount(username, password, title, email, firstName, lastName, address, phoneNumber, dateOfBirth);
            //if title = customer, create notification, create login setting, create activity log
            if (title.equals("customer")) {
                if (createCustomerDetailController.createDetail(json).getStatusCode() != HttpStatus.OK) {
                    return new ResponseEntity<>("Failed to create customer detail", HttpStatus.BAD_REQUEST);
                }
            }
                if (createLoginSettingController.createLoginSetting(json).getStatusCode() != HttpStatus.OK) {
                    return new ResponseEntity<>("Failed to create login setting", HttpStatus.BAD_REQUEST);
                }
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/Verify")
    public ResponseEntity<String> verifyOTP(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            boolean otp = jsonNode.get("otp").asBoolean();
            userAccountService.verifyOTP(username, otp);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
