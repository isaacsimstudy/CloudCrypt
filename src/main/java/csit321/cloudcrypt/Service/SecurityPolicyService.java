package csit321.cloudcrypt.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;


@Service
public interface SecurityPolicyService {

    public String createSecurityPolicy(String policyName,
                                               String description,
                                               String enforcementLevel,
                                               String policyType,
                                               Map<String, String> parameters,
                                               String status);

    public String updateSecurityPolicy(UUID id, Map<String, String> updates);

    public String deleteSecurityPolicy(UUID id);

    @Transactional
    public String getSecurityPolicy();
}
