package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.SecurityPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecurityPolicyRepository extends JpaRepository<SecurityPolicy, UUID> {
}