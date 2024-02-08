package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.NotificationSetting;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, UUID> {
    List<NotificationSetting> findNotificationSettingByUserAccountAndNotificationType(UserAccount userAccount, String param);

    List<NotificationSetting> findAllByUserAccount(UserAccount userAccount);


    void deleteNotificationSettingByUserAccountAndNotificationType(UserAccount userAccount, String notificationType);
}