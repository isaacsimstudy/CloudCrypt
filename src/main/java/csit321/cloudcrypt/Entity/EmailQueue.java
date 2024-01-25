package csit321.cloudcrypt.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "email_queue")
public class EmailQueue {
    @Id
    @Column(name = "email_id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_account", nullable = false)
    private UserAccount userAccount;

    @Column(name = "email_subject", nullable = false)
    private String emailSubject;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "time_sent", nullable = false)
    private OffsetDateTime timeSent;

    @Column(name = "email_body", nullable = false)
    private String emailBody;

}