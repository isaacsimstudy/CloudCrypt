package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Repository.NotificationSettingRepository;
import csit321.cloudcrypt.Service.implementation.NotificationSettingServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationSettingTests {

    @Autowired
    private NotificationSettingServiceImpl notificationSettingServiceImpl;

    @Autowired
    private NotificationSettingRepository notificationSettingRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    // Test the getNotificationSetting method using test for username and param
    @Test
    public void testGetNotificationSetting() {
        // Test the getNotificationSetting method using test for username and param
        String notificationSetting = notificationSettingServiceImpl.getNotificationSetting(userAccountRepository.findUserAccountByUsername("customer1").orElseThrow(), "download");
        String notificationSetting2 = notificationSettingServiceImpl.getNotificationSetting(userAccountRepository.findUserAccountByUsername("admin1").orElseThrow(), "upload");
        if (notificationSetting == null) {
            System.out.println("Notification setting not found");
            throw new IllegalArgumentException("Notification setting not found");
        }
        else {
            System.out.println(notificationSetting);
        }
        if (notificationSetting2 == null) {
            System.out.println("Notification setting not found");
            throw new IllegalArgumentException("Notification setting not found");
        }
        else {
            System.out.println(notificationSetting2);
        }
    }

    // Test the updateNotificationSetting method using test for userAccount, notificationType, notificationMethod, status, and notificationFrequency
    @Test
    public void testUpdateNotificationSetting() {
        // Test the updateNotificationSetting method using test for userAccount, notificationType, notificationMethod, status, and notificationFrequency
        String notificationSetting = notificationSettingServiceImpl.updateNotificationSetting(userAccountRepository.findUserAccountByUsername("customer1").orElseThrow(), "download" , "upload", "email", "active", "weekly");
        String notificationSetting2 = notificationSettingServiceImpl.updateNotificationSetting(userAccountRepository.findUserAccountByUsername("admin1").orElseThrow(), "upload" , "download", "email", "active", "weekly");
        if (notificationSetting == null) {
            System.out.println("Notification setting not updated");
            throw new IllegalArgumentException("Notification setting not updated");
        }
        else {
            System.out.println(notificationSetting + " updated");
        }
        if (notificationSetting2 == null) {
            System.out.println("Notification setting not updated");
            throw new IllegalArgumentException("Notification setting not updated");
        }
        else {
            System.out.println(notificationSetting2);
        }
    }

    // Test the createNotificationSetting method using test for userAccount, notificationType, notificationMethod, status, and notificationFrequency
    @Test
    public void testCreateNotificationSetting() {
        // Test the createNotificationSetting method using test for userAccount, notificationType, notificationMethod, status, and notificationFrequency
        String notificationSetting = notificationSettingServiceImpl.createNotificationSetting(userAccountRepository.findUserAccountByUsername("customer1").orElseThrow(), "download", "email", "active", "daily");
        String notificationSetting2 = notificationSettingServiceImpl.createNotificationSetting(userAccountRepository.findUserAccountByUsername("admin1").orElseThrow(), "upload", "email", "active", "daily");
        if (notificationSetting == null) {
            System.out.println("Notification setting not created");
            throw new IllegalArgumentException("Notification setting not created");
        }
        else {
            System.out.println(notificationSetting);
        }
        if (notificationSetting2 == null) {
            System.out.println("Notification setting not created");
            throw new IllegalArgumentException("Notification setting not created");
        }
        else {
            System.out.println(notificationSetting2);
        }
    }

    // Test the deleteNotificationSetting method using test for userAccount and param
    @Test
    public void testDeleteNotificationSetting() {
        // Test the deleteNotificationSetting method using test for userAccount and param
        String notificationSetting = notificationSettingServiceImpl.deleteNotificationSetting(userAccountRepository.findUserAccountByUsername("customer1").orElseThrow(), "upload");
        String notificationSetting2 = notificationSettingServiceImpl.deleteNotificationSetting(userAccountRepository.findUserAccountByUsername("admin1").orElseThrow(), "download");
        if (notificationSetting == null) {
            System.out.println("Notification setting not deleted");
            throw new IllegalArgumentException("Notification setting not deleted");
        }
        else {
            System.out.println(notificationSetting);
        }
        if (notificationSetting2 == null) {
            System.out.println("Notification setting not deleted");
            throw new IllegalArgumentException("Notification setting not deleted");
        }
        else {
            System.out.println(notificationSetting2);
        }
    }

}
