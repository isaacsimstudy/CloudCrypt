package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.stereotype.Service;

@Service
public interface NotificationSettingService {

    public String getNotificationSetting(UserAccount userAccount, String param);

    public String updateNotificationSetting(UserAccount userAccount,
                                          String notificationType,
                                          String newNotificationType,
                                          String notificationMethod,
                                          String status,
                                          String notificationFrequency);

    public String createNotificationSetting(UserAccount userAccount,
                                            String notificationType,
                                            String notificationMethod,
                                            String status,
                                            String notificationFrequency);

    public String deleteNotificationSetting(UserAccount userAccount, String param);
}
