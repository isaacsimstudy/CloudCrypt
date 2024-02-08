package csit321.cloudcrypt.Service.implementation;

import csit321.cloudcrypt.Entity.CustomerDetail;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.CustomerDetailRepository;
import csit321.cloudcrypt.Service.CustomerDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerDetailServiceImpl implements CustomerDetailService {
    private final CustomerDetailRepository customerDetailRepository;

    @Autowired
    public CustomerDetailServiceImpl(CustomerDetailRepository customerDetailRepository) {
        this.customerDetailRepository = customerDetailRepository;
    }

    @Override
    public String createDetail(UserAccount userAccount, String subTier) {
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setId(UUID.randomUUID());
        customerDetail.setUserAccount(userAccount);
        customerDetail.setSubTier(subTier);
        try{
            customerDetailRepository.save(customerDetail);
        } catch (Exception e) {
            return "Customer detail record not created";
        }
        return "Customer detail record created";
    }

    @Transactional
    @Override
    public String readDetail(UserAccount userAccount) {
        CustomerDetail customerDetail = customerDetailRepository.findCustomerDetailByUserAccount(userAccount);
        if (customerDetail == null)
            return "Customer detail not found";
        return customerDetail.getSubTier();
    }

    @Override
    public String updateDetail(UserAccount userAccount, String newSubTier) {
        CustomerDetail customerDetail = customerDetailRepository.findCustomerDetailByUserAccount(userAccount);
        if (newSubTier != "premium" && newSubTier != "free" )
            throw new IllegalArgumentException("Invalid sub-tier.");
        customerDetail.setSubTier(newSubTier);
        return customerDetailRepository.save(customerDetail).getSubTier();
    }

    @Override
    public String deleteDetail(UserAccount userAccount) {
        CustomerDetail customerDetail = customerDetailRepository.findCustomerDetailByUserAccount(userAccount);
        if (customerDetail == null)
            throw new IllegalArgumentException("Sub-tier does not exist.");
        customerDetailRepository.delete(customerDetail);
        return "Customer detail record deleted";
    }
}
