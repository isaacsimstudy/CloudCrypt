package csit321.cloudcrypt.Controller.Customer;

import csit321.cloudcrypt.Entity.ActivityLog;
import csit321.cloudcrypt.Repository.ActivityLogRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class DeleteActivityLogController {
    private final ActivityLogService activityLogService;
    private final ActivityLogRepository activityLogRepository;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public DeleteActivityLogController(ActivityLogService activityLogService,
                                       ActivityLogRepository activityLogRepository,
                                       UserAccountRepository userAccountRepository) {
        this.activityLogService = activityLogService;
        this.activityLogRepository = activityLogRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/DeleteActivityLog")
    public ResponseEntity<String> deleteActivityLog(@RequestBody String uuid) {
        try {
            ActivityLog activityLog = activityLogRepository.findActivityLogById(java.util.UUID.fromString(uuid));
            if (activityLog == null) {
                return new ResponseEntity<>("Activity log not found.", HttpStatus.BAD_REQUEST);
            }
            String result = activityLogService.deleteLog(activityLog);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
