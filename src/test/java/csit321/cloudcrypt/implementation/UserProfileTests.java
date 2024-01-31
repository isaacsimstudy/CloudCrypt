package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Entity.UserProfile;
import csit321.cloudcrypt.Service.implementation.User.UserProfileServiceImpl;
import csit321.cloudcrypt.Service.User.UserProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserProfileTests {

        @Autowired
        private UserProfileServiceImpl userProfileServiceImpl;

        @Test
        public void testCreateProfile() {
            UserProfile userProfile = userProfileServiceImpl.createProfile("admin", "admin");

        }

//        @Test
//        public void testGetProfile() {
//            UserProfile userProfile = userProfileService.createProfile("test", "test");
//            assert(userProfileService.getProfile("test", "test").equals(userProfile));
//        }
//
//        @Test
//        public void testUpdateProfile() {
//            UserProfile userProfile = userProfileService.createProfile("test", "test");
//            assert(userProfileService.updateProfile("test", "test").equals(userProfile));
//        }
//
//        @Test
//        public void testDeleteProfile() {
//            UserProfile userProfile = userProfileService.createProfile("test", "test");
//            assert(userProfileService.deleteProfile("test", "test").equals(userProfile));
//        }
}
