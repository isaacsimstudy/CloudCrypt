package csit321.cloudcrypt.Service.User;

import org.springframework.stereotype.Service;
import csit321.cloudcrypt.Entity.UserProfile;

@Service
public interface UserProfileService {

    UserProfile createUserProfile(String privilege, String title);

    String readUserProfile(String param);

    String updateUserProfile(String targetTitle, String privilege, String title);

    String suspendUserProfile(String title);
}
