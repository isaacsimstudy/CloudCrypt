package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Service.implementation.User.UserAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserAccountTests {

    @Autowired
    private UserAccountServiceImpl userAccountServiceImplementation;



    @Test
    public void testCreateAccount() {
        // Test the createAccount method using test for firstname, lastname, username, password, email, address, and phone number
        UserAccount userAccount = userAccountServiceImplementation.createAccount("customerTest", "test", "customer", "test", "test", "test", "test", "12456789", "2021-01-01");
        UserAccount userAccount2 = userAccountServiceImplementation.createAccount("AdminTest2", "test", "admin", "test", "test", "test", "test", "12456789", "2021-01-01");
        if (userAccount == null) {
            System.out.println("User account not created");
            new IllegalArgumentException("User account not created");
        }
        else {
            System.out.println(userAccount);
        }
        if (userAccount2 == null) {
            System.out.println("User account not created");
            new IllegalArgumentException("User account not created");
        }
        else {
            System.out.println(userAccount2);
        }
    }

    // :TODO:
    // Test the readAccount method using test for username
    // Lazy initialization error
    @Test
    public void testReadAccount() {
        // Test the readAccount method using test for username
        String userAccount = userAccountServiceImplementation.readAccount("customerTest");
        String userAccount2 = userAccountServiceImplementation.readAccount("AdminTest2");
        String userAccountList = userAccountServiceImplementation.readAccount("all");
        if (userAccount == null) {
            System.out.println("User account not found");
            new IllegalArgumentException("User account not found");
        }
        else {
            System.out.println(userAccount);
        }
        if (userAccount2 == null) {
            System.out.println("User account not found");
            new IllegalArgumentException("User account not found");
        }
        else {
            System.out.println(userAccount2);
        }
        if (userAccountList == null) {
            System.out.println("User account not found");
            new IllegalArgumentException("User account not found");
        }
        else {
            System.out.println(userAccountList);
        }
    }

    @Test
    public void testUpdateAccount() {
        // Test the updateAccount method using test for username, password, email, firstname, lastname, address, and phone number
        String userAccount = userAccountServiceImplementation.updateAccount("customerTestupdate", "test", "test", "test", "test", "test", "12456789");
        String userAccount2 = userAccountServiceImplementation.updateAccount("AdminTest2update", "test", "test", "test", "test", "test", "12456789");
        if (userAccount == null) {
            System.out.println("User account not updated");
            new IllegalArgumentException("User account not updated");
        }
        else {
            System.out.println(userAccount);
        }
        if (userAccount2 == null) {
            System.out.println("User account not updated");
            new IllegalArgumentException("User account not updated");
        }
        else {
            System.out.println(userAccount2);
        }
    }

    @Test
    public void testSuspendAccount() {
        // Test the suspendAccount method using test for username
        String userAccount = userAccountServiceImplementation.suspendAccount("customerTest");
        String userAccount2 = userAccountServiceImplementation.suspendAccount("AdminTest2");
        if (userAccount == null) {
            System.out.println("User account not suspended");
            new IllegalArgumentException("User account not suspended");
        }
        else {
            System.out.println(userAccount);
        }
        if (userAccount2 == null) {
            System.out.println("User account not suspended");
            new IllegalArgumentException("User account not suspended");
        }
        else {
            System.out.println(userAccount2);
        }
    }
}
