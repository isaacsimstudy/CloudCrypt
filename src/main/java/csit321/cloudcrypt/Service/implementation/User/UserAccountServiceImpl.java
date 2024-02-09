package csit321.cloudcrypt.Service.implementation.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserProfile;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Repository.UserProfileRepository;
import csit321.cloudcrypt.Service.User.UserAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import csit321.cloudcrypt.Entity.UserAccount;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserAccountServiceImpl implements UserAccountService{

    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, UserProfileRepository userProfileRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserAccount createAccount(String username,
                                     String password,
                                     String title,
                                     String email,
                                     String firstName,
                                     String lastName,
                                     String address,
                                     String phoneNumber,
                                     String dateOfBirth) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(UUID.randomUUID());
        userAccount.setIsActive(true);
        UserProfile userProfile = userProfileRepository.findUserProfileByTitle(title).orElse(null);
        if (userProfile == null)
            throw new IllegalArgumentException("Title does not exist.");
        userAccount.setUserProfile(userProfile);
        userAccount.setUsername(username);
        userAccount.setPasswordHash(password);
        userAccount.setEmail(email.toLowerCase());
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setAddress(address);
        userAccount.setPhoneNumber(phoneNumber);
        userAccount.setDateOfBirth(LocalDate.parse(dateOfBirth));
        userAccount.setTimeCreated(OffsetDateTime.now());
        userAccount.setTimeLastLogin(OffsetDateTime.now());
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    @Override
    public String readAccount(String param) {
        List<UserAccount> userAccountList = switch(param) {
            case "active" -> userAccountRepository.findUserAccountsByIsActiveTrue();
            case "privilege" -> userAccountRepository.findAllByUserProfile_Privilege(param);
            case "all" -> userAccountRepository.findAll();
            default -> {
                UserAccount userAccount = userAccountRepository.findUserAccountByUsername(param).orElse(null);
                if (userAccount == null)
                    throw new IllegalArgumentException("User account not found: " + param);
                yield List.of(userAccount);
            }
        };

        ArrayNode an = objectMapper.createArrayNode();
        for (UserAccount userAccount : userAccountList) {
            ObjectNode on = objectMapper.createObjectNode();
            on.put("id", userAccount.getId().toString());
            on.put("username", userAccount.getUsername());
            on.put("title", userAccount.getUserProfile().getTitle());
            on.put("email", userAccount.getEmail());
            on.put("firstName", userAccount.getFirstName());
            on.put("lastName", userAccount.getLastName());
            on.put("address", userAccount.getAddress());
            on.put("phoneNumber", userAccount.getPhoneNumber());
            on.put("isActive", userAccount.getIsActive());
            an.add(on);
        }
        return an.toString();
    }

    @Override
    public String updateAccount(String username,
                                String password,
                                String title,
                                String email,
                                String firstName,
                                String lastName,
                                String address,
                                String phoneNumber,
                                String dateOfBirth) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElse(null);
        if (userAccount == null)
            return "User account not found.";
        if (password != null)
            userAccount.setPasswordHash(password);
        if (email != null)
            userAccount.setEmail(email.toLowerCase());
        if (firstName != null)
            userAccount.setFirstName(firstName);
        if (lastName != null)
            userAccount.setLastName(lastName);
        if (address != null)
            userAccount.setAddress(address);
        if (phoneNumber != null)
            userAccount.setPhoneNumber(phoneNumber);
        return "Successful change " + userAccountRepository.save(userAccount).toString();
    }

    @Override
    public String suspendAccount(String targetUsername) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(targetUsername).orElse(null);
        if (userAccount == null)
            return "User account not found.";
        if (!userAccount.getIsActive())
            return "Account already suspended.";
        userAccount.setIsActive(false);
        return "Account suspended: " + userAccountRepository.save(userAccount).toString();
    }

    //TODO: Write Test Method
    @Override
    public String logInAccount(String username, String password) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(username).orElse(null);
        if (userAccount == null)
            return "User account not found.";
        if (!userAccount.getIsActive())
            return "Account suspended.";
        if (!userAccount.getPasswordHash().equals(password))
            return "Incorrect password.";
        userAccount.setTimeLastLogin(OffsetDateTime.now());
        userAccountRepository.save(userAccount);
        return "Success";
    }
}
