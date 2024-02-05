package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, UUID> {
    List<ActivityLog> findActivityLogByUserAccountID(UUID uuid);

    List<ActivityLog> findActivityLogByUserAccountIDAndActivityType(UUID uuid, String param);

    List<ActivityLog> findActivityLogByUserAccountIDAndStatus(UUID uuid, String param);

    List<ActivityLog> findActivityLogByUserAccountIDAndCloudFileID(UUID uuid, UUID cloudFileID);
}