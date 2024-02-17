package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.NotificationSettingService;
import csit321.cloudcrypt.Service.User.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class CreateNotificationSettingController {

    private final NotificationSettingService notificationSettingService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public CreateNotificationSettingController(NotificationSettingService notificationSettingService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.notificationSettingService = notificationSettingService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/CreateNotificationSetting")
    public ResponseEntity<String> createNotificationSetting(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            try {
                String notificationType = jsonNode.get("notificationType").asText();
                String notificationMethod = jsonNode.get("notificationMethod").asText();
                String status = jsonNode.get("status").asText();
                String notificationFrequency = jsonNode.get("notificationFrequency").asText();
                String notificationSetting =
                        notificationSettingService.createNotificationSetting(userAccount,
                                notificationType,
                                notificationMethod,
                                status,
                                notificationFrequency);
                return new ResponseEntity<>(notificationSetting, HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
