package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.stereotype.Service;

@Service
public interface CustomerDetailService {

    // Create a new customer detail
    String createDetail(UserAccount userAccount , String subTier);

    // Read a customer detail
    String readDetail(UserAccount userAccount);

    // Update a customer detail
    String updateDetail(UserAccount userAccount, String newSubTier);

    // Delete a customer detail
    String deleteDetail(UserAccount userAccount);
}
