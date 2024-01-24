package csit321.cloudcrypt.Entity;

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
@Table(name = "customer_details")
public class CustomerDetail {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @Column(name = "sub_tier", nullable = false, length = 75)
    private String subTier;

}