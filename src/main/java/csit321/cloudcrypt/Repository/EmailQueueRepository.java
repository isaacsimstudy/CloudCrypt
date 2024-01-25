package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.EmailQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailQueueRepository extends JpaRepository<EmailQueue, UUID> {
}