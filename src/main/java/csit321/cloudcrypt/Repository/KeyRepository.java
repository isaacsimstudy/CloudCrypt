package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KeyRepository extends JpaRepository<Key, UUID> {


}