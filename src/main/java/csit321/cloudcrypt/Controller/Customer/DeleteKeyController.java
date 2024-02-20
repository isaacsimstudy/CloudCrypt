package csit321.cloudcrypt.Controller.Customer;


import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Repository.KeyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.KeyService;
import csit321.cloudcrypt.Service.User.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/Customer")
public class DeleteKeyController {
    private final KeyService keyService;
    private final KeyRepository keyRepository;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public DeleteKeyController(KeyService keyService,
                               KeyRepository keyRepository,
                               UserAccountRepository userAccountRepository) {
        this.keyService = keyService;
        this.keyRepository = keyRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(path = "/DeleteKey")
    public ResponseEntity<String> deleteKey(@RequestBody String uuid) {
        try {
            Key key = keyRepository.findKeyById(java.util.UUID.fromString(uuid));
            if (key == null) {
                return new ResponseEntity<>("Key not found.", HttpStatus.BAD_REQUEST);
            }
            String result = keyService.deleteKey(key);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
