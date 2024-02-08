package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Entity.ActivityLog;
import csit321.cloudcrypt.Repository.ActivityLogRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.implementation.ActivityLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ActivityLogTests {

    @Autowired
    private ActivityLogServiceImpl activityLogServiceImpl;
    @Autowired
    private ActivityLogRepository activityLogRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    // Test the createLog method using test for userAccount, activityType, status, and cloudFileID
    @Test
    public void testCreateLog() {
        // Test the createLog method using test for userAccount, activityType, status, and cloudFileID
        String activityLog = activityLogServiceImpl.createLog("customer1", "download", "success", "null");
        String activityLog2 = activityLogServiceImpl.createLog("admin1", "upload", "success", "null");
        if (activityLog == null) {
            System.out.println("Activity log not created");
            throw new IllegalArgumentException("Activity log not created");
        }
        else {
            System.out.println(activityLog);
        }
        if (activityLog2 == null) {
            System.out.println("Activity log not created");
            throw new IllegalArgumentException("Activity log not created");
        }
        else {
            System.out.println(activityLog2);
        }
    }

    // Test the readLog method using test for param, userName, and fileName
    @Test
    public void testReadLog() {
        // Test the readLog method using test for param, userName, and fileName
        String activityLog = activityLogServiceImpl.readLog("download", "customer1", "null");
        String activityLog2 = activityLogServiceImpl.readLog("upload", "admin1", "null");
        if (activityLog == null) {
            System.out.println("Activity log not found");
            throw new IllegalArgumentException("Activity log not found");
        }
        else {
            System.out.println(activityLog);
        }
        if (activityLog2 == null) {
            System.out.println("Activity log not found");
            throw new IllegalArgumentException("Activity log not found");
        }
        else {
            System.out.println(activityLog2);
        }
    }

    // Test the deleteLog method using test for activityID
    @Test
    public void testDeleteLog() {
        // Test the deleteLog method using test for activityID
        List<ActivityLog> activityLogList = activityLogRepository.findActivityLogByUserAccount(userAccountRepository.findUserAccountByUsername("customer1").orElseThrow());
        if (activityLogList == null) {
            System.out.println("Activity log not found");
            throw new IllegalArgumentException("Activity log not found");
        }
        for (ActivityLog activityLog : activityLogList) {
            String activityLog3 = activityLogServiceImpl.deleteLog(activityLog);
            if (activityLog3 == null) {
                System.out.println("Activity log not deleted");
                throw new IllegalArgumentException("Activity log not deleted");
            }
            else {
                System.out.println(activityLog3);
            }
        }
    }
}
