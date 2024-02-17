package csit321.cloudcrypt.Service.implementation.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.UserProfile;
import csit321.cloudcrypt.Repository.UserProfileRepository;
import csit321.cloudcrypt.Service.User.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userProfileRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    // Create a new user profile
    // It works by creating a new user profile object and assigning it the respective values
    public UserProfile createUserProfile(String privilege, String title) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(UUID.randomUUID());
        userProfile.setPrivilege(privilege);
        userProfile.setTitle(title);
        userProfile.setIsActive(true);
        return userProfileRepository.save(userProfile);
    }


    @Override
    public String readUserProfile(String param) {
        if (param.equals("titles"))
            return userProfileRepository.findAllTitles().toString();
        if (param.equals("privileges"))
            return userProfileRepository.findAllPrivileges().toString();

        List<UserProfile> userProfileList = switch(param) {
            case "admin", "owner", "customer" -> userProfileRepository.findUserProfilesByPrivilege(param);
            case "active" -> userProfileRepository.findUserProfilesByIsActiveTrue();
            case "all" -> userProfileRepository.findAll();
            default -> {
                UserProfile userProfile = userProfileRepository.findUserProfileByTitle(param).orElse(null);
                yield List.of(userProfile);
            }
        };

        ArrayNode an = objectMapper.createArrayNode();
        for (UserProfile userProfile : userProfileList) {
            ObjectNode on = objectMapper.createObjectNode();
            on.put("title", userProfile.getTitle());
            on.put("privilege", userProfile.getPrivilege());
            on.put("isActive", userProfile.getIsActive().toString());
            an.add(on);
        }
        return an.toString();
    }


    @Override
    public String updateUserProfile(String targetTitle, String privilege, String title) {
        UserProfile userProfile = userProfileRepository.findUserProfileByTitle(targetTitle).orElse(null);
        if (userProfile == null)
            return "User profile not found.";
        if (title.equalsIgnoreCase("customer") ||
                targetTitle.equalsIgnoreCase("customer") ||
                userProfile.getPrivilege().equalsIgnoreCase("customer"))
            return "Cannot modify customer.";

        switch (privilege) {
            case "admin", "owner" -> {
            }
            case "customer" -> {
                return "Reserved privilege.";
            }
            default -> {
                return "Invalid privilege.";
            }
        }

        userProfile.setPrivilege(privilege);
        userProfile.setTitle(title);
        return "Update Successful: " + userProfileRepository.save(userProfile);
    }

    public String suspendUserProfile(String targetTitle) {
        if (targetTitle.equalsIgnoreCase("customer"))
            return "Cannot modify customer.";
        UserProfile userProfile = userProfileRepository.findUserProfileByTitle(targetTitle).orElse(null);
        if (userProfile == null)
            return "User profile not found.";
        if (!userProfile.getIsActive())
            return "User profile already suspended.";

        String privilege = userProfile.getPrivilege();
        if (userProfileRepository.findUserProfilesByPrivilege(privilege).size() == 1)
            return "Cannot suspend last user with privilege: " + privilege;

        userProfile.setIsActive(false);
        return "Suspend Successful: " + userProfileRepository.save(userProfile);
    }

}
