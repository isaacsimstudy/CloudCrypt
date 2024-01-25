package csit321.cloudcrypt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "file_info")
public class FileInfo {
    @Id
    @Column(name = "log_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id", nullable = false)
    private Cloud file;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account", nullable = false)
    private UserAccount userAccount;

    @Column(name = "original_size", nullable = false)
    private Long originalSize;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "original_hash", nullable = false)
    private String originalHash;

    @Column(name = "time_uploaded", nullable = false)
    private OffsetDateTime timeUploaded;

    @Column(name = "last_modified", nullable = false)
    private OffsetDateTime lastModified;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_owner", nullable = false)
    private UserAccount fileOwner;

    @Column(name = "encryption_type", nullable = false)
    private String encryptionType;

    @Column(name = "tags", nullable = false)
    private String tags;

}