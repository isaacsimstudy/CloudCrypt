package Repository;

import Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
}