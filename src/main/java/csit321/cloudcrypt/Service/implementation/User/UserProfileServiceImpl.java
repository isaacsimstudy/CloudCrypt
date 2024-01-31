package csit321.cloudcrypt.Service.implementation.User;

import org.springframework.stereotype.Service;
import csit321.cloudcrypt.Entity.UserProfile;
import csit321.cloudcrypt.Service.User.UserProfileService;
import csit321.cloudcrypt.Repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile createProfile(String privilege, String title) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(UUID.randomUUID());
        userProfile.setPrivilege(privilege);
        userProfile.setTitle(title);
        userProfile.setIsActive(true);
        return userProfileRepository.save(userProfile);
    }

//    @Override
//    public UserProfile getProfile(String privilege, String title) {
//        return null;
//    }
//
//    @Override
//    public UserProfile updateProfile(String privilege, String title) {
//        return null;
//    }
//
//    @Override
//    public UserProfile deleteProfile(String privilege, String title) {
//        return null;
//    }
}
