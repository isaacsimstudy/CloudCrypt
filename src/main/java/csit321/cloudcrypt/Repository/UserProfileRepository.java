package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    List<UserProfile> findAll();

    @Query(value = "SELECT title FROM user_profile\n", nativeQuery = true)
    List<String> findAllTitles();

    @Query(value = "SELECT DISTINCT privilege FROM user_profile\n", nativeQuery = true)
    List<String> findAllPrivileges();

    List<UserProfile> findUserProfilesByPrivilege(String privilege);

    List<UserProfile> findUserProfilesByIsActiveTrue();

    Optional<UserProfile> findUserProfileByTitle(String s);
}