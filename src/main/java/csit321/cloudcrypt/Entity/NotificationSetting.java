package csit321.cloudcrypt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification_settings")
public class NotificationSetting {
    @EmbeddedId
    private NotificationSettingId id;

    @MapsId("userAccount")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account", nullable = false)
    private UserAccount userAccount;

    @Column(name = "notification_method", nullable = false)
    private String notificationMethod;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "notification_frequency", nullable = false)
    private String notificationFrequency;

}