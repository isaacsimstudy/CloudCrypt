package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Service.implementation.User.UserAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAccountTests {

    @Autowired
    private UserAccountServiceImpl userAccountServiceImplementation;



    @Test
    public void testCreateAccount() {
        UserAccount userAccount = userAccountServiceImplementation.createAccount("test", "test", "test@test.com", "test", "test", "test", "98765432");
        assert(userAccount.getUsername().equals("test"));
        assert(userAccount.getPasswordHash().equals("test"));
        assert(userAccount.getEmail().equals("test"));
        assert(userAccount.getFirstName().equals("test"));
        assert(userAccount.getLastName().equals("test"));
        assert(userAccount.getAddress().equals("test"));
        assert(userAccount.getPhoneNumber().equals("test"));
        assert(userAccount.getTimeCreated() != null);
        assert(userAccount.getTimeLastLogin() != null);
    }
}
