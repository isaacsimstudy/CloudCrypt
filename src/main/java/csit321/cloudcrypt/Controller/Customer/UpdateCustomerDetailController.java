package csit321.cloudcrypt.Controller.Customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.CustomerDetailService;
import csit321.cloudcrypt.Service.User.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class UpdateCustomerDetailController {

    private final CustomerDetailService customerDetailService;
    private final UserAccountService userAccountService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UpdateCustomerDetailController(CustomerDetailService customerDetailService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.customerDetailService = customerDetailService;
        this.userAccountService = userAccountService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/Update")
    public ResponseEntity<String> updateDetail(@RequestBody String json) {
        try {
            System.out.println("json: " + json);
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            String subTier = jsonNode.get("subTier").asText();
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            if (userAccount == null) {
                return new ResponseEntity<>("Invalid username: Username does not exist", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            String result = customerDetailService.updateDetail(userAccount, subTier);
            return new ResponseEntity<>("New Sub-Tier is: " + result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
