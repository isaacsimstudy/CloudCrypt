package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
}