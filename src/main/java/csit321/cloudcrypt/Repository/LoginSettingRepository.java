package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.LoginSetting;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoginSettingRepository extends JpaRepository<LoginSetting, UUID> {
    Optional<LoginSetting> findLoginSettingByUserAccount(UserAccount user);
}