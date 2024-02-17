package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.SecurityPolicy;
import csit321.cloudcrypt.Entity.UserAccount;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;


@Service
public interface SecurityPolicyService {

    public SecurityPolicy createSecurityPolicy(UserAccount userAccount,
                                               String policyName,
                                               String description,
                                               String enforcementLevel,
                                               String policyType,
                                               Map<String, Object> parameters,
                                               String status);

    public String updateSecurityPolicy(UUID id, Map<String, Object> updates);

    public String deleteSecurityPolicy(UserAccount userAccount);

    @Transactional
    public String getSecurityPolicy(UserAccount userAccount);
}
