package Repository;

import Entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KeyRepository extends JpaRepository<Key, UUID> {
}