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
public class ReadLoginSettingController {
    private final LoginSettingService loginSettingService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public ReadLoginSettingController(LoginSettingService loginSettingService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.loginSettingService = loginSettingService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/ReadLoginSetting")
    public ResponseEntity<String> readLoginSetting(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            UserAccount user = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            String loginSetting = loginSettingService.getLoginSetting(user, jsonNode.get("param").asText());
            return new ResponseEntity<>(loginSetting, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
