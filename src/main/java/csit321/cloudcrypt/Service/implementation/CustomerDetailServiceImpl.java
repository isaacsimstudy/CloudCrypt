package csit321.cloudcrypt.Service.implementation;

import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.CustomerDetailRepository;
import csit321.cloudcrypt.Service.CustomerDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import csit321.cloudcrypt.Entity.CustomerDetail;

import java.util.UUID;

@Service
public class CustomerDetailServiceImpl implements CustomerDetailService {
    private final CustomerDetailRepository customerDetailRepository;

    @Autowired
    public CustomerDetailServiceImpl(CustomerDetailRepository customerDetailRepository) {
        this.customerDetailRepository = customerDetailRepository;
    }

    @Transactional
    @Override
    public CustomerDetail createDetail(String subTier) {
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setId(UUID.randomUUID());
        customerDetail.setSubTier(subTier);
        return customerDetailRepository.save(customerDetail);
    }

    @Transactional
    @Override
    public String readDetail(UserAccount userAccount) {
        CustomerDetail customerDetail = customerDetailRepository.findCustomerDetailByUUID(userAccount.getId());
        return customerDetail.getSubTier();
    }

    @Transactional
    @Override
    public String updateDetail(UserAccount userAccount, String newSubTier) {
        CustomerDetail customerDetail = customerDetailRepository.findCustomerDetailByUUID(userAccount.getId());
        if (newSubTier != "premium" || newSubTier != "free" )
            throw new IllegalArgumentException("Invalid sub-tier.");
        customerDetail.setSubTier(newSubTier);
        return customerDetailRepository.save(customerDetail).getSubTier();
    }

    @Transactional
    @Override
    public String deleteDetail(UserAccount userAccount) {
        CustomerDetail customerDetail = customerDetailRepository.findCustomerDetailByUUID(userAccount.getId());
        if (customerDetail == null)
            throw new IllegalArgumentException("Sub-tier does not exist.");
        customerDetailRepository.delete(customerDetail);
        return "Sub-tier deleted.";
    }
}
