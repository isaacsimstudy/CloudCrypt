package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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