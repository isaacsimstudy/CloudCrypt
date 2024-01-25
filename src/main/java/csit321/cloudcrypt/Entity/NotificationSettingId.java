package csit321.cloudcrypt.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class NotificationSettingId implements Serializable {
    private static final long serialVersionUID = 2669823985358491220L;
    @Column(name = "user_account", nullable = false)
    private UUID userAccount;

    @Column(name = "notification_type", nullable = false)
    private String notificationType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NotificationSettingId entity = (NotificationSettingId) o;
        return Objects.equals(this.userAccount, entity.userAccount) &&
                Objects.equals(this.notificationType, entity.notificationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAccount, notificationType);
    }

}