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
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "privilege", nullable = false)
    private String privilege;

    @Column(name = "title", nullable = false)
    private String title;

}