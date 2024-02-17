package csit321.cloudcrypt.Service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.LoginSetting;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.LoginSettingRepository;
import csit321.cloudcrypt.Service.LoginSettingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class LoginSettingServiceImpl implements LoginSettingService {

    public final LoginSettingRepository loginSettingRepository;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    public LoginSettingServiceImpl(LoginSettingRepository loginSettingRepository) {
        this.loginSettingRepository = loginSettingRepository;
    }

    @Transactional
    @Override
    public String getLoginSetting(UserAccount user, String param) {
        LoginSetting loginSetting = loginSettingRepository.findLoginSettingByUserAccount(user).orElseThrow();
        ObjectNode objectNode = objectMapper.createObjectNode();
        switch(param) {
            case "loginAttempts":
                objectNode.put("loginAttempts", loginSetting.getLoginAttempts());
                break;
            case "loginStatus":
                objectNode.put("loginStatus", loginSetting.getLoginStatus());
                break;
            case "loginTime":
                objectNode.put("loginTime", loginSetting.getLoginTime().toString());
                break;
            case "twoFactorAuth":
                objectNode.put("twoFactorAuth", loginSetting.getTwoFactorAuth());
                break;
            case "all":
                // Return all login settings
                objectNode.put("User", user.getUsername());
                objectNode.put("loginAttempts", loginSetting.getLoginAttempts());
                objectNode.put("loginStatus", loginSetting.getLoginStatus());
                objectNode.put("loginTime", loginSetting.getLoginTime().toString());
                objectNode.put("twoFactorAuth", loginSetting.getTwoFactorAuth());
                break;
            default:
                return "Invalid parameter";
        }
        return objectNode.toString();
    }

    @Override
    public String updateLoginSetting(UserAccount user,
                                     String loginAttempts,
                                     String loginStatus,
                                     String loginTime,
                                     String twoFactorAuth) {
        LoginSetting loginSetting = loginSettingRepository.findLoginSettingByUserAccount(user).orElseThrow();
        if (loginAttempts != null) {
            loginSetting.setLoginAttempts(Integer.parseInt(loginAttempts));
        }
        if (loginStatus != null) {
            loginSetting.setLoginStatus(loginStatus);
        }
        if (loginTime != null) {
            loginSetting.setLoginTime(OffsetDateTime.parse(loginTime));
        }
        if (twoFactorAuth != null) {
            loginSetting.setTwoFactorAuth(twoFactorAuth);
        }
        loginSettingRepository.save(loginSetting);
        System.out.println("Login setting updated");
        return "Success";
    }

    @Override
    public String createLoginSetting(UserAccount user,
                                     String loginAttempts,
                                     String loginStatus,
                                     String loginTime,
                                     String twoFactorAuth) {
        LoginSetting loginSetting = new LoginSetting();
        loginSetting.setId(UUID.randomUUID());
        loginSetting.setUserAccount(user);
        loginSetting.setLoginAttempts(Integer.parseInt(loginAttempts));
        loginSetting.setLoginStatus(loginStatus);
        loginSetting.setLoginTime(OffsetDateTime.parse(loginTime));
        loginSetting.setTwoFactorAuth(twoFactorAuth);
        loginSettingRepository.save(loginSetting);
        System.out.println("Login setting created");
        return "Success";
    }

    @Override
    public String deleteLoginSetting(UserAccount user) {
        LoginSetting loginSetting = loginSettingRepository.findLoginSettingByUserAccount(user).orElseThrow();
        loginSettingRepository.delete(loginSetting);
        System.out.println("Login setting deleted");
        return "Success";
    }

}
