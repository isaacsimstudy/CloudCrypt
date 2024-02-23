package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Entity.SecurityPolicy;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.implementation.SecurityPolicyServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class SecurityPolicyTests {

    @Autowired
    private SecurityPolicyServiceImpl securityPolicyServiceImplementation;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void testCreateSecurityPolicy() {
        // Set necessary fields on userAccount
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername("customerOne").orElseThrow();

        String policyName = "Test Policy";
        String description = "Test Description";
        String enforcementLevel = "medium";
        String policyType = "encryption";
        Map<String, String> parameters = Map.of("key", "value"); // Example parameters
        String status = "active";

        String result = securityPolicyServiceImplementation.createSecurityPolicy(policyName, description, enforcementLevel, policyType, parameters, status);

        String results = result.toString();
        System.out.println(results);
    }

    @Test
    public void testUpdateSecurityPolicy() {
        Map<String, String> parameters = Map.of("policyName", "Update Policy",
                "parameters", """
               test1:test1,test2:test2\s""" );
        // Test the updateSecurityPolicy method using test for id and updates
        String securityPolicy = securityPolicyServiceImplementation.updateSecurityPolicy(UUID.fromString("81561a2d-7fc7-4149-bbee-96eee44aec65"), parameters);
        //String securityPolicy2 = securityPolicyServiceImplementation.updateSecurityPolicy(UUID.fromString("bc76b628-b785-46df-b69b-f747e4d0abe9"), parameters);
        if (securityPolicy == null) {
            System.out.println("Security policy not updated");
            throw new IllegalArgumentException("Security policy not updated");
        }
        else {
            System.out.println(securityPolicy);
        }
//        if (securityPolicy2 == null) {
//            System.out.println("Security policy not updated");
//            throw new IllegalArgumentException("Security policy not updated");
//        }
//        else {
//            System.out.println(securityPolicy2);
//        }
    }

    @Test
    public void testDeleteSecurityPolicy() {
        // Test the deleteSecurityPolicy method using test for userAccount
        String securityPolicy = securityPolicyServiceImplementation.deleteSecurityPolicy(java.util.UUID.fromString("6693b134-9145-45aa-a810-1de8f30ea0be"));
        String securityPolicy2 = securityPolicyServiceImplementation.deleteSecurityPolicy(java.util.UUID.fromString("bc76b628-b785-46df-b69b-f747e4d0abe9"));
        if (securityPolicy == null) {
            System.out.println("Security policy not deleted");
            //throw new IllegalArgumentException("Security policy not deleted");
        }
        else {
            System.out.println(securityPolicy);
        }
        if (securityPolicy2 == null) {
            System.out.println("Security policy not deleted");
            //throw new IllegalArgumentException("Security policy not deleted");
        }
        else {
            System.out.println(securityPolicy2);
        }
    }

    @Test
    public void testGetSecurityPolicy() {
        // Test the getSecurityPolicy method using test for userAccount
        String securityPolicy = securityPolicyServiceImplementation.getSecurityPolicy();
        String securityPolicy2 = securityPolicyServiceImplementation.getSecurityPolicy();
        if (securityPolicy == null) {
            System.out.println("Security policy not found");
            //throw new IllegalArgumentException("Security policy not found");
        }
        else {
            System.out.println(securityPolicy);
        }
        if (securityPolicy2 == null) {
            System.out.println("Security policy not found");
            //throw new IllegalArgumentException("Security policy not found");
        }
        else {
            System.out.println(securityPolicy2);
        }
    }
}
