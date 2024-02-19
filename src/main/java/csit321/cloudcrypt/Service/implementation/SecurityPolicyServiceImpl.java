package csit321.cloudcrypt.Service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Entity.SecurityPolicy;
import csit321.cloudcrypt.Repository.SecurityPolicyRepository;


import csit321.cloudcrypt.Service.SecurityPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class SecurityPolicyServiceImpl implements SecurityPolicyService {

    public final SecurityPolicyRepository securityPolicyRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    public SecurityPolicyServiceImpl(SecurityPolicyRepository securityPolicyRepository) {
        this.securityPolicyRepository = securityPolicyRepository;
    }

    @Override
    public String createSecurityPolicy(UserAccount userAccount,
                                     String policyName,
                                     String description,
                                     String enforcementLevel,
                                     String policyType,
                                     Map<String, String> parameters,
                                     String status) {
        SecurityPolicy securityPolicy = new SecurityPolicy();
        securityPolicy.setUserAccount(userAccount);
        securityPolicy.setId(UUID.randomUUID());
        securityPolicy.setPolicyName(policyName);
        securityPolicy.setDescription(description);
        securityPolicy.setEnforcementLevel(enforcementLevel);
        securityPolicy.setPolicyType(policyType);
        securityPolicy.setParameters(parameters);
        securityPolicy.setStatus(status);
        securityPolicyRepository.save(securityPolicy);
        return "Success";
    }

    @Override
    public String updateSecurityPolicy(UUID id, Map<String, String> updates) {
        SecurityPolicy policy = securityPolicyRepository.findById(id).orElse(null);
        if (policy == null) {
            return "SecurityPolicy not found";
        }

        // Iterate over the map and apply updates
        updates.forEach((key, value) -> {
            switch (key) {
                case "policyName" -> policy.setPolicyName((String) value);
                case "description" -> policy.setDescription((String) value);
                case "parameters" -> {
                    //convert value to Map<String, String>
                    Map<String, String> parameters = convertStringToMap((String) value);
                    policy.setParameters(parameters);
                }
                // Add more cases as needed for other fields
                default -> { }
            }
        });

        securityPolicyRepository.save(policy);
        return "SecurityPolicy updated";
    }

    public Map<String, String> convertStringToMap(String str) {
        // Convert a string to a map
        Map<String, String> map = new HashMap<>();
        StringTokenizer st = new StringTokenizer(str, ",");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            String[] keyValue = token.split(":");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    @Override
    public String deleteSecurityPolicy(UserAccount userAccount) {
        // Delete all security policies related to a user account
        List<SecurityPolicy> securityPolicies = securityPolicyRepository.findAllByUserAccount(userAccount);
        securityPolicyRepository.deleteAll(securityPolicies);
        return "SecurityPolicy deleted";
    }

    @Transactional
    @Override
    public String getSecurityPolicy(UserAccount userAccount) {
        // Get all security policies related to a user account
        ArrayNode policies = objectMapper.createArrayNode();
        List<SecurityPolicy> securityPolicies = securityPolicyRepository.findAllByUserAccount(userAccount);
        for (SecurityPolicy policy : securityPolicies) {
            ObjectNode policyNode = objectMapper.createObjectNode();
            policyNode.put("id", policy.getId().toString());
            policyNode.put("policyName", policy.getPolicyName());
            policyNode.put("description", policy.getDescription());
            policyNode.put("enforcementLevel", policy.getEnforcementLevel());
            policyNode.put("policyType", policy.getPolicyType());
            policyNode.put("parameters", objectMapper.valueToTree(policy.getParameters()));
            policyNode.put("status", policy.getStatus());
            policies.add(policyNode);
        }
        return policies.toString();
    }
}
