package Repository;

import Entity.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, UUID> {
}