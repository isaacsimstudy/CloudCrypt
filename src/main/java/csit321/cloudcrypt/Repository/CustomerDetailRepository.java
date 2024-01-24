package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, UUID> {
}