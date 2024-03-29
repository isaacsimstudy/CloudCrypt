package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.ActivityLog;
import org.springframework.stereotype.Service;

@Service
public interface ActivityLogService {

        // Create a new activity log
        String createLog(String username, String action, String status, String fileName);

        // Read an activity log
        String readLog(String param, String userName, String fileName);

        // Delete an activity log
        String deleteLog(ActivityLog activityLog);
}
