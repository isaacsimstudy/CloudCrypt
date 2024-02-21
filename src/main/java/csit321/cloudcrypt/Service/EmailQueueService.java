package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public interface EmailQueueService {

    // CRUD operations for EmailQueue

    public String createEmailQueue(UserAccount userAccount, String subject, String body);

    public String readEmailQueue(String param);

    public String updateEmailQueue(UUID uuid, Map<String, String> updates);

    public String deleteEmailQueue(Map<String, String> param);

    // Schedule Methods for EmailQueue
}
