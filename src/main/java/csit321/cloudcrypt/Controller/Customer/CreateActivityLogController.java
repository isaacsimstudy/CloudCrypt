package csit321.cloudcrypt.Controller.Customer;

import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.ActivityLogService;
import csit321.cloudcrypt.Service.User.UserAccountService;
import csit321.cloudcrypt.Entity.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class CreateActivityLogController {
    private final ActivityLogService activityLogService;
    private final UserAccountService userAccountService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public CreateActivityLogController(ActivityLogService activityLogService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.activityLogService = activityLogService;
        this.userAccountService = userAccountService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/CreateActivityLog")
    public ResponseEntity<String> createActivityLog(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            String activityLog = activityLogService.createLog(username,
                                                        jsonNode.get("action").asText(),
                                                        jsonNode.get("status").asText(),
                                                        jsonNode.get("fileName").asText());
            return new ResponseEntity<>(activityLog, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
