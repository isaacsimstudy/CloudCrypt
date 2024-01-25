package csit321.cloudcrypt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cloud")
public class Cloud {
    @Id
    @Column(name = "file_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account", nullable = false)
    private UserAccount userAccount;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "encrypted_file", nullable = false)
    private byte[] encryptedFile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "key_id", nullable = false)
    private Key key;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "checksum", nullable = false)
    private String checksum;

}