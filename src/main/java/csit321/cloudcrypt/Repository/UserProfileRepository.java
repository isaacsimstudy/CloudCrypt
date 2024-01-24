package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

}