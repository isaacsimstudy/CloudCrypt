package csit321.cloudcrypt.Service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.NotificationSetting;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.NotificationSettingRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.NotificationSettingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class NotificationSettingServiceImpl implements NotificationSettingService{

    private final NotificationSettingRepository notificationSettingRepository;

    private final UserAccountRepository userAccountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public NotificationSettingServiceImpl(NotificationSettingRepository notificationSettingRepository,
                                          UserAccountRepository userAccountRepository) {
        this.notificationSettingRepository = notificationSettingRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public String getNotificationSetting(UserAccount userAccount, String param) {
        List<NotificationSetting> notificationSettingsList = switch (param) {
            case "login", "logout", "upload", "download", "delete" , "all" ->
                    notificationSettingRepository.findNotificationSettingByUserAccountAndNotificationType(userAccount, param);
            default -> notificationSettingRepository.findAllByUserAccount(userAccount);
        };


        ArrayNode an = objectMapper.createArrayNode();
        for (NotificationSetting notificationSetting : notificationSettingsList) {
            ObjectNode on = objectMapper.createObjectNode();
            on.put("notification_type", notificationSetting.getNotificationType());
            on.put("notification_method", notificationSetting.getNotificationMethod());
            on.put("status", notificationSetting.getStatus());
            on.put("notification_frequency", notificationSetting.getNotificationFrequency());
            an.add(on);
        }
        return an.toString();
    }

    @Override
    public String updateNotificationSetting(UserAccount userAccount,
                                          String notificationType,
                                          String newNotificationType,
                                          String notificationMethod,
                                          String status,
                                          String notificationFrequency) {
        List<NotificationSetting> notificationSetting = notificationSettingRepository.findNotificationSettingByUserAccountAndNotificationType(userAccount, notificationType);
        for (NotificationSetting ns : notificationSetting) {
            if (newNotificationType != null)
                ns.setNotificationType(newNotificationType);
            if (notificationMethod != null)
                ns.setNotificationMethod(notificationMethod);
            if (status != null)
                ns.setStatus(status);
            if (notificationFrequency != null)
                ns.setNotificationFrequency(notificationFrequency);
            notificationSettingRepository.save(ns);
        }
        return "Notification setting updated.";
    }

    @Override
    public String createNotificationSetting(UserAccount userAccount,
                                            String notificationType,
                                            String notificationMethod,
                                            String status,
                                            String notificationFrequency) {
        NotificationSetting notificationSetting = new NotificationSetting();
        notificationSetting.setId(UUID.randomUUID());
        notificationSetting.setUserAccount(userAccount);
        notificationSetting.setNotificationType(notificationType);
        notificationSetting.setNotificationMethod(notificationMethod);
        notificationSetting.setStatus(status);
        notificationSetting.setNotificationFrequency(notificationFrequency);
        notificationSettingRepository.save(notificationSetting);
        return "Notification setting created.";
    }

    @Override
    public String deleteNotificationSetting(UserAccount userAccount, String param) {
        List<NotificationSetting> notificationSetting = switch (param) {
            case "login", "logout", "upload", "download", "delete", "share", "unshare" ->
                    notificationSettingRepository.findNotificationSettingByUserAccountAndNotificationType(userAccount, param);
            default -> notificationSettingRepository.findAllByUserAccount(userAccount);
        };
        notificationSettingRepository.deleteAll(notificationSetting);
        return "Notification setting deleted.";
    }
}
