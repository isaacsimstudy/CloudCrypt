package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.LoginSettingService;
import csit321.cloudcrypt.Service.User.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class UpdateLoginSettingController {
    private final LoginSettingService loginSettingService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UpdateLoginSettingController(LoginSettingService loginSettingService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.loginSettingService = loginSettingService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/UpdateLoginSetting")
    public ResponseEntity<String> updateLoginSetting(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            UserAccount user = userAccountRepository.findUserAccountByUsername(username).orElseThrow();

            // Read optional parameters with null check
            String loginAttempts = jsonNode.has("loginAttempts") ? jsonNode.get("loginAttempts").asText() : null;
            String loginStatus = jsonNode.has("loginStatus") ? jsonNode.get("loginStatus").asText() : null;
            String loginTime = jsonNode.has("loginTime") ? jsonNode.get("loginTime").asText() : null;
            String twoFactorAuth = jsonNode.has("twoFactorAuth") ? jsonNode.get("twoFactorAuth").asText() : null;

            String loginSetting = loginSettingService.updateLoginSetting(user,
                    loginAttempts,
                    loginStatus,
                    loginTime,
                    twoFactorAuth);
            return new ResponseEntity<>(loginSetting, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
