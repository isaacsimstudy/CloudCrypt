package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findUserAccountByUsername(String username);

    List<UserAccount> findUserAccountsByIsActiveTrue();

    List<UserAccount> findAllByUserProfile_Privilege(final String privilege);

    Optional<UserAccount> findUserAccountByEmail(String email);

    @Query(value = """
            SELECT * FROM user_account
            where username = ?1
            and password_hash = crypt(?2, password_hash)
            """, nativeQuery = true
    )
    Optional<UserAccount> findUserAccountByUsernameAndPassword(final String username, final String password);
}