package csit321.cloudcrypt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "key")
public class Key {
    @Id
    @Column(name = "key_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account", nullable = false)
    private UserAccount userAccount;

    @Column(name = "name", nullable = false)
    private String keyname;

    @Column(name = "password_hash", nullable = false, length = 72)
    private String passwordHash;


}