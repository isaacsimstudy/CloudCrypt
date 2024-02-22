package csit321.cloudcrypt.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "security_policies")
public class SecurityPolicy {
    @Id
    @Column(name = "policy_id", nullable = false)
    private UUID id;

    @Column(name = "policy_name", nullable = false)
    private String policyName;

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "enforcement_level", nullable = false, length = 50)
    private String enforcementLevel;

    @Column(name = "policy_type", nullable = false, length = 50)
    private String policyType;

    @Column(name = "parameters", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> parameters;

    @Column(name = "status", length = 50)
    private String status;

}