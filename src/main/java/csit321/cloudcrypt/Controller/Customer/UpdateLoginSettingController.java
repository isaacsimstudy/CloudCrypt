package csit321.cloudcrypt.Controller.Customer;

import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.LoginSettingService;
import csit321.cloudcrypt.Service.User.UserAccountService;
import csit321.cloudcrypt.Entity.UserAccount;

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
            String loginSetting = loginSettingService.updateLoginSetting(user,
                                                        jsonNode.get("loginAttempts").asText(),
                                                        jsonNode.get("loginStatus").asText(),
                                                        jsonNode.get("loginTime").asText(),
                                                        jsonNode.get("twoFactorAuth").asText());
            return new ResponseEntity<>(loginSetting, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
