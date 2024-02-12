package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.LoginSettingRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import csit321.cloudcrypt.Entity.LoginSetting;

public interface LoginSettingService {

        public String getLoginSetting(UserAccount userAccount, String param);

        public String updateLoginSetting(UserAccount user,
                                         String loginAttempts,
                                         String loginStatus,
                                         String loginTime,
                                         String twoFactorAuth);

        public String createLoginSetting(UserAccount user,
                                         String loginAttempts,
                                         String loginStatus,
                                         String loginTime,
                                         String twoFactorAuth);

        public String deleteLoginSetting(UserAccount user);
}
