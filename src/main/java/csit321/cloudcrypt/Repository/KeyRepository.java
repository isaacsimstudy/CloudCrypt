package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface KeyRepository extends JpaRepository<Key, UUID> {

    List<Key> findAllByUserAccount(UserAccount userAccount);

    Key findKeyById(UUID KeyID);
}