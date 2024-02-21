package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.Cloud;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CloudRepository extends JpaRepository<Cloud, UUID> {
    Optional<Cloud> findCloudByFileName(String fileName);

    List<Cloud> findAllByUserAccount(UserAccount userAccount);

    List<Cloud> findCloudByStatus(String value);

    void deleteCloudByFileName(String value);
}