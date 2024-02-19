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
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @Column(name = "keyname", nullable = false)
    private String keyname;

    @Column(name = "password_hash", nullable = false, length = 72)
    private String passwordHash;

}