package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Repository.LoginSettingRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.implementation.LoginSettingServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginSettingTests {

    @Autowired
    private LoginSettingServiceImpl loginSettingServiceImpl;
    @Autowired
    private LoginSettingRepository loginSettingRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    // Test the getLoginSetting method using test for userAccount and param
    @Test
    public void testGetLoginSetting() {
        // Test the getLoginSetting method using test for userAccount and param
        String loginSetting1 = loginSettingServiceImpl.getLoginSetting(userAccountRepository.findUserAccountByUsername("customerOne").orElseThrow(), "all");
        String loginSetting2 = loginSettingServiceImpl.getLoginSetting(userAccountRepository.findUserAccountByUsername("adminOne").orElseThrow(), "all");
        if (loginSetting1 == null) {
            System.out.println("Login setting not found");
            throw new IllegalArgumentException("Login setting not found");
        }
        else {
            System.out.println(loginSetting1);
        }
        if (loginSetting2 == null) {
            System.out.println("Login setting not found");
            throw new IllegalArgumentException("Login setting not found");
        }
        else {
            System.out.println(loginSetting2);
        }
    }

    // Test the updateLoginSetting method using test for userAccount, loginAttempts, loginStatus, loginTime, and twoFactorAuth
    @Test
    public void testUpdateLoginSetting() {
        // Test the updateLoginSetting method using test for userAccount, loginAttempts, loginStatus, loginTime, and twoFactorAuth
        String loginSetting = loginSettingServiceImpl.updateLoginSetting(userAccountRepository.findUserAccountByUsername("customerOne").orElseThrow(), "3", "active", "2021-10-10T12:00:00Z", "inactive");
        String loginSetting2 = loginSettingServiceImpl.updateLoginSetting(userAccountRepository.findUserAccountByUsername("adminOne").orElseThrow(), "3", "active", "2021-10-10T12:00:00Z", "inactive");
        if (loginSetting == null) {
            System.out.println("Login setting not updated");
            throw new IllegalArgumentException("Login setting not updated");
        }
        else {
            System.out.println(loginSetting);
        }
        if (loginSetting2 == null) {
            System.out.println("Login setting not updated");
            throw new IllegalArgumentException("Login setting not updated");
        }
        else {
            System.out.println(loginSetting2);
        }
    }

    // Test the createLoginSetting method using test for userAccount, loginAttempts, loginStatus, loginTime, and twoFactorAuth
    @Test
    public void testCreateLoginSetting() {
        // Test the createLoginSetting method using
        String loginSetting = loginSettingServiceImpl.createLoginSetting(userAccountRepository.findUserAccountByUsername("customerOne").orElseThrow(), "3", "inactive", "2021-10-10T12:00:00Z", "active");
        String loginSetting2 = loginSettingServiceImpl.createLoginSetting(userAccountRepository.findUserAccountByUsername("adminOne").orElseThrow(), "3", "inactive", "2021-10-10T12:00:00Z", "active");
        if (loginSetting == null) {
            System.out.println("Login setting not created");
            throw new IllegalArgumentException("Login setting not created");
        } else {
            System.out.println(loginSetting);
        }
    }

    // Test the deleteLoginSetting method using test for userAccount
    @Test
    public void testDeleteLoginSetting() {
        // Test the deleteLoginSetting method using test for userAccount
        String loginSetting = loginSettingServiceImpl.deleteLoginSetting(userAccountRepository.findUserAccountByUsername("customerOne").orElseThrow());
        String loginSetting2 = loginSettingServiceImpl.deleteLoginSetting(userAccountRepository.findUserAccountByUsername("adminOne").orElseThrow());
        if (loginSetting == null) {
            System.out.println("Login setting not deleted");
            throw new IllegalArgumentException("Login setting not deleted");
        }
        else {
            System.out.println(loginSetting);
        }
        if (loginSetting2 == null) {
            System.out.println("Login setting not deleted");
            throw new IllegalArgumentException("Login setting not deleted");
        }
        else {
            System.out.println(loginSetting2);
        }
    }
}
