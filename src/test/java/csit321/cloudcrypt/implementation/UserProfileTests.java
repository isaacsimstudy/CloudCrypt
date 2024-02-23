package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Entity.UserProfile;
import csit321.cloudcrypt.Service.implementation.User.UserProfileServiceImpl;
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
            //UserProfile userProfile = userProfileServiceImpl.createUserProfile("admin", "admin");
            //UserProfile userProfile2 = userProfileServiceImpl.createUserProfile("customer", "customer");
            //UserProfile userProfile3 = userProfileServiceImpl.createUserProfile("owner", "owner");
            UserProfile userProfile4 = userProfileServiceImpl.createUserProfile("admin", "adminTwo");
            //System.out.println(userProfile);
            //System.out.println(userProfile2);
            //System.out.println(userProfile3);
            System.out.println(userProfile4);
        }

        @Test
        public void testGetProfile() {
            // Test that user profile is retrieved according to database requirements ie. privilege and/or title
            // must be "admin", "customer", "owner"
            // Refer to schema.sql for database requirements
            String userProfile = userProfileServiceImpl.readUserProfile("admin");
            String userProfile2 = userProfileServiceImpl.readUserProfile("customer");
            String userProfile3 = userProfileServiceImpl.readUserProfile("owner");
            String userProfile4 = userProfileServiceImpl.readUserProfile("titles");
            String userProfile5 = userProfileServiceImpl.readUserProfile("privileges");
            System.out.println(userProfile);
            System.out.println(userProfile2);
            System.out.println(userProfile3);
            System.out.println(userProfile4);
            System.out.println(userProfile5);
        }

        @Test
        public void testUpdateProfile() {
            // Test that user profile is updated according to database requirements ie. privilege and/or title
            // must be "admin", "customer", "owner"
            // Refer to schema.sql for database requirements
            String userProfile = userProfileServiceImpl.updateUserProfile("admin", "admin", "senior admin");
            String userProfile2 = userProfileServiceImpl.updateUserProfile("customer", "customer", "customer");
            String userProfile3 = userProfileServiceImpl.updateUserProfile("owner", "owner", "co-owner");
            System.out.println(userProfileServiceImpl.readUserProfile("senior admin"));
            System.out.println(userProfileServiceImpl.readUserProfile("customer"));
            System.out.println(userProfileServiceImpl.readUserProfile("co-owner"));
            System.out.println(userProfile);
            System.out.println(userProfile2);
            System.out.println(userProfile3);
        }

        @Test
        public void testDeleteProfile() {
            // Test that user profile is deleted according to database requirements ie. privilege and/or title
            // must be "admin", "customer", "owner"
            // Refer to schema.sql for database requirements
            String userProfile = userProfileServiceImpl.suspendUserProfile("senior admin");
            String userProfile2 = userProfileServiceImpl.suspendUserProfile("customer");
            System.out.println(userProfile);
            System.out.println(userProfile2);
            System.out.println(userProfileServiceImpl.readUserProfile("senior admin"));
            System.out.println(userProfileServiceImpl.readUserProfile("customer"));
        }
}
