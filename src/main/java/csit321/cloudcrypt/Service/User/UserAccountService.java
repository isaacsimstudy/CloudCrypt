package csit321.cloudcrypt.Service.User;

import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.stereotype.Service;

@Service
public interface UserAccountService {

    UserAccount createAccount(String username, String password, String title, String email, String firstName, String lastName, String address, String phoneNumber, String dateOfBirth);

    String readAccount(String username);

    String updateAccount(String username, String password, String email, String firstName, String lastName, String address, String phoneNumber);

    String suspendAccount(String username);
}