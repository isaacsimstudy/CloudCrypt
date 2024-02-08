package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.NotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, UUID> {
}