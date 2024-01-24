package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KeyRepository extends JpaRepository<Key, UUID> {
}