package csit321.cloudcrypt.Service.implementation.User;

import csit321.cloudcrypt.Entity.UserProfile;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Repository.UserProfileRepository;
import csit321.cloudcrypt.Service.User.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import csit321.cloudcrypt.Entity.UserAccount;

@Service
public class UserAccountServiceImpl implements UserAccountService{

    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;


    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, UserProfileRepository userProfileRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserAccount createAccount(String username, String password, String email, String firstName, String lastName, String address, String phoneNumber) {
        UserAccount userAccount = new UserAccount();

        userAccount.setUsername(username);
        userAccount.setPasswordHash(password);
        userAccount.setEmail(email);
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setAddress(address);
        userAccount.setPhoneNumber(phoneNumber);
        return userAccountRepository.save(userAccount);
    }
}
