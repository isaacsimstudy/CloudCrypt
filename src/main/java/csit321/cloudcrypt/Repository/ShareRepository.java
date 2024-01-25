package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShareRepository extends JpaRepository<Share, UUID> {
}