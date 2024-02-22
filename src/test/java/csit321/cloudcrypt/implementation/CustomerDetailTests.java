package csit321.cloudcrypt.implementation;

import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.implementation.CustomerDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerDetailTests {

    @Autowired
    private CustomerDetailServiceImpl customerDetailServiceImpl;

    @Autowired
    private UserAccountRepository userAccountRepository;

    // Test the createCustomerDetail method using test for userAccount, firstName, lastName, and email
    @Test
    public void testCreateCustomerDetail() {
        // Test the createCustomerDetail method using test for userAccount and subTier
        String customerDetail = customerDetailServiceImpl.createDetail(userAccountRepository.findUserAccountByUsername("customer9").orElseThrow(), "premium");
        String customerDetail2 = customerDetailServiceImpl.createDetail(userAccountRepository.findUserAccountByUsername("customerOne0").orElseThrow(), "free");
        if (customerDetail == null) {
            System.out.println("Customer detail not created test");
            throw new IllegalArgumentException("Customer detail not created test");
        } else {
            System.out.println(customerDetail);
        }
        if (customerDetail2 == null) {
            System.out.println("Customer detail not created test");
            throw new IllegalArgumentException("Customer detail not created test");
        } else {
            System.out.println(customerDetail2);
        }
    }

    // Test the readCustomerDetail method using test for userAccount
    @Test
    public void testReadCustomerDetail() {
        // Test the readCustomerDetail method using test for userAccount
        String customerDetail = customerDetailServiceImpl.readDetail(userAccountRepository.findUserAccountByUsername("customer9").orElseThrow());
        String customerDetail2 = customerDetailServiceImpl.readDetail(userAccountRepository.findUserAccountByUsername("customerOne0").orElseThrow());
        if (customerDetail == null) {
            System.out.println("Customer detail not found");
            throw new IllegalArgumentException("Customer detail not found");
        } else {
            System.out.println(customerDetail);
        }
        if (customerDetail2 == null) {
            System.out.println("Customer detail not found");
            throw new IllegalArgumentException("Customer detail not found");
        } else {
            System.out.println(customerDetail2);
        }
    }

    // Test the updateCustomerDetail method using test for userAccount and newSubTier
    @Test
    public void testUpdateCustomerDetail() {
        // Test the updateCustomerDetail method using test for userAccount and newSubTier
        String customerDetail = customerDetailServiceImpl.updateDetail(userAccountRepository.findUserAccountByUsername("customer9").orElseThrow(), "free");
        String customerDetail2 = customerDetailServiceImpl.updateDetail(userAccountRepository.findUserAccountByUsername("customerOne0").orElseThrow(), "premium");
        if (customerDetail == null) {
            System.out.println("Customer detail not updated");
            throw new IllegalArgumentException("Customer detail not updated");
        } else {
            System.out.println(customerDetail);
        }
        if (customerDetail2 == null) {
            System.out.println("Customer detail not updated");
            throw new IllegalArgumentException("Customer detail not updated");
        } else {
            System.out.println(customerDetail2);
        }
    }

    // Test the deleteCustomerDetail method using test for userAccount
    @Test
    public void testDeleteCustomerDetail() {
        // Test the deleteCustomerDetail method using test for userAccount
        String customerDetail = customerDetailServiceImpl.deleteDetail(userAccountRepository.findUserAccountByUsername("customer9").orElseThrow());
        String customerDetail2 = customerDetailServiceImpl.deleteDetail(userAccountRepository.findUserAccountByUsername("customerOne0").orElseThrow());
        if (customerDetail == null) {
            System.out.println("Customer detail not deleted");
            throw new IllegalArgumentException("Customer detail not deleted");
        } else {
            System.out.println(customerDetail);
        }
        if (customerDetail2 == null) {
            System.out.println("Customer detail not deleted");
            throw new IllegalArgumentException("Customer detail not deleted");
        } else {
            System.out.println(customerDetail2);
        }
    }
}
