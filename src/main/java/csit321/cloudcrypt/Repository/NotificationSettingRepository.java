package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.NotificationSetting;
import csit321.cloudcrypt.Entity.NotificationSettingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, NotificationSettingId> {
}