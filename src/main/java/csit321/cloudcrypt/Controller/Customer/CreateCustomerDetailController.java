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
public class CreateCustomerDetailController {
    private final CustomerDetailService customerDetailService;
    private final UserAccountService userAccountService;
    private final ObjectMapper objectMapper;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public CreateCustomerDetailController(CustomerDetailService customerDetailService, UserAccountService userAccountService, UserAccountRepository userAccountRepository) {
        this.customerDetailService = customerDetailService;
        this.userAccountService = userAccountService;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/Create")
    public ResponseEntity<String> createDetail(@RequestBody String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            String username = jsonNode.get("username").asText();
            String subTier = "free";
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElseThrow();
            if (userAccount == null) {
                return new ResponseEntity<>("Invalid username: Username does not exist", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            String customerDetail = customerDetailService.createDetail(userAccount, subTier);
            if (!customerDetail.matches("Customer detail record created")) {
                return new ResponseEntity<>(customerDetail, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else
                return new ResponseEntity<>(customerDetail, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
