package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.UserAccount;

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
