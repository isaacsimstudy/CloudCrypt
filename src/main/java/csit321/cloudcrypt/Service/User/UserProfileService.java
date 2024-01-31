package csit321.cloudcrypt.Service.User;

import org.springframework.stereotype.Service;
import csit321.cloudcrypt.Entity.UserProfile;

@Service
public interface UserProfileService {

    UserProfile createProfile(String privilege, String title);

//    UserProfile getProfile(String privilege, String title);
//
//    UserProfile updateProfile(String privilege, String title);
//
//    UserProfile deleteProfile(String privilege, String title);
}
