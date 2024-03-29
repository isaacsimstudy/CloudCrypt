package csit321.cloudcrypt.Service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.ActivityLog;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.ActivityLogRepository;
import csit321.cloudcrypt.Repository.CloudRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.ActivityLogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    public final ActivityLogRepository activityLogRepository;
    private final UserAccountRepository userAccountRepository;
    private final CloudRepository cloudRepository;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository,
                                  UserAccountRepository userAccountRepository,
                                  CloudRepository cloudRepository) {
        this.activityLogRepository = activityLogRepository;
        this.userAccountRepository = userAccountRepository;
        this.cloudRepository = cloudRepository;
    }

    @Override
    public String createLog(String username,
                            String action,
                            String status,
                            String fileName) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUserAccount(userAccountRepository.findUserAccountByUsername(username).orElseThrow());
        activityLog.setActivityType(action);
        activityLog.setStatus(status);
        activityLog.setActivityTime(OffsetDateTime.now());
        activityLog.setId(UUID.randomUUID());
        if (fileName.equals("null")) {
            activityLog.setCloud(null);
            activityLogRepository.save(activityLog);
            return "Activity log created.";
        }
        activityLog.setCloud(cloudRepository.findCloudByFileName(fileName).orElseThrow());
        activityLogRepository.save(activityLog);
        return "Activity log created.";
    }

    @Transactional
    @Override
    public String readLog(String param, String userName, String fileName) {
        UserAccount account = userAccountRepository.findUserAccountByUsername(userName).orElseThrow();
        // find all logs for user defined by param
        List<ActivityLog> activityLogList = switch(param) {
            case "login", "logout", "upload", "download", "delete", "share", "unshare" ->
                activityLogRepository.findActivityLogByUserAccountAndActivityType(account, param);

            case "success", "failure" ->
                activityLogRepository.findActivityLogByUserAccountAndStatus(account, param);

            case "file" ->
                activityLogRepository.findActivityLogByUserAccountAndCloud(account,
                        cloudRepository.findCloudByFileName(fileName).orElseThrow());
            default ->
                activityLogRepository.findActivityLogByUserAccount(account);

        };
        ArrayNode an = objectMapper.createArrayNode();
        for (ActivityLog activityLog : activityLogList) {
            ObjectNode on = objectMapper.createObjectNode();
            on.put("user", activityLog.getUserAccount().getUsername());
            on.put("action", activityLog.getActivityType());
            on.put("status", activityLog.getStatus());
            on.put("time", activityLog.getActivityTime().toString());
            if (activityLog.getCloud() != null)
                on.put("file", activityLog.getCloud().getFileName());
            an.add(on);
        }
        return an.toString();
    }

    // Delete an activity log
    @Override
    public String deleteLog(ActivityLog activityLog) {
        activityLogRepository.delete(activityLog);
        return "Activity log deleted.";
    }
}
