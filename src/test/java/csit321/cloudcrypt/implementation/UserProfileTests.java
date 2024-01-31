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
                // Create a new user profile
                // Test that user profile is created according to database requirements ie. privilege and/or title
                // must be "admin", "customer", "owner"
                // Refer to schema.sql for database requirements
            UserProfile userProfile = userProfileServiceImpl.createProfile("admin", "admin");
            assert(userProfile.getPrivilege().equals("admin"));
            assert(userProfile.getTitle().equals("admin"));
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
