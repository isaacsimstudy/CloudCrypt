package csit321.cloudcrypt.Controller.Customer;

import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.NotificationSettingService;
import csit321.cloudcrypt.Service.User.UserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class UpdateNotificationSettingController {

    private final NotificationSettingService notificationSettingService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UpdateNotificationSettingController(NotificationSettingService notificationSettingService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.notificationSettingService = notificationSettingService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/UpdateNotificationSetting")
    public ResponseEntity<String> updateNotificationSetting(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            String notificationSetting =
                    notificationSettingService.updateNotificationSetting(
                            userAccount,
                            jsonNode.get("notificationType").asText(),
                            jsonNode.get("newNotificationType").asText(),
                            jsonNode.get("notificationMethod").asText(),
                            jsonNode.get("status").asText(),
                            jsonNode.get("notificationFrequency").asText());
            return new ResponseEntity<>(notificationSetting, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
