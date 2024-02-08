package csit321.cloudcrypt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "notification_settings")
public class NotificationSetting {
    @Id
    @Column(name = "notification_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account", nullable = false)
    private UserAccount userAccount;

    @Column(name = "notification_type", nullable = false)
    private String notificationType;

    @Column(name = "notification_method", nullable = false)
    private String notificationMethod;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "notification_frequency", nullable = false)
    private String notificationFrequency;

}