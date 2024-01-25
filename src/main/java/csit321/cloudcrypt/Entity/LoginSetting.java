package csit321.cloudcrypt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "login_settings")
public class LoginSetting {
    @Id
    @Column(name = "login_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account", nullable = false)
    private UserAccount userAccount;

    @Column(name = "login_attempts", nullable = false)
    private Integer loginAttempts;

    @Column(name = "login_status", nullable = false)
    private String loginStatus;

    @Column(name = "login_time", nullable = false)
    private OffsetDateTime loginTime;

    @Column(name = "two_factor_auth", nullable = false)
    private String twoFactorAuth;

}