package csit321.cloudcrypt.Service;

import org.springframework.stereotype.Service;
import csit321.cloudcrypt.Entity.ActivityLog;

@Service
public interface ActivityLogService {

        // Create a new activity log
        String createLog(String username, String action, String status, String fileName);

        // Read an activity log
        String readLog(String param, String userName, String fileName);
}
