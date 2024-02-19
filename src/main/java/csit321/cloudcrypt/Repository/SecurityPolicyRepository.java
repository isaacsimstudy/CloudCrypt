package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.SecurityPolicy;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SecurityPolicyRepository extends JpaRepository<SecurityPolicy, UUID> {

    void deleteAllByUserAccount(UserAccount userAccount);

    List<SecurityPolicy> findAllByUserAccount(UserAccount userAccount);
}