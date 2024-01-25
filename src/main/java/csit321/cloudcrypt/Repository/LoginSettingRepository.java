package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.LoginSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginSettingRepository extends JpaRepository<LoginSetting, UUID> {
}