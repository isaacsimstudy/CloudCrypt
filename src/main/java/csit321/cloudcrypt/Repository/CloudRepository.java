package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.Cloud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CloudRepository extends JpaRepository<Cloud, UUID> {
    Optional<Cloud> findCloudByFileName(String fileName);
}