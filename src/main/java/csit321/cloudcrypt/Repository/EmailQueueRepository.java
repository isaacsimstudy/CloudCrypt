package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.EmailQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmailQueueRepository extends JpaRepository<EmailQueue, UUID> {
    Optional<EmailQueue> findEmailQueueByUserAccount_Username(String param);

    List<EmailQueue> findEmailQueueByStatus(String pending);

    void deleteEmailQueueByUserAccount_Username(String value);
}