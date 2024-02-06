package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, UUID> {
    List<ActivityLog> findActivityLogByUserAccountUUID(UUID uuid);

    List<ActivityLog> findActivityLogByUserAccountUUIDAndActivityType(UUID uuid, String param);

    List<ActivityLog> findActivityLogByUserAccountUUIDAndStatus(UUID uuid, String param);

    List<ActivityLog> findActivityLogByUserAccountUUIDAndCloudFileUUID(UUID uuid, UUID cloudFileID);

    ActivityLog findActivityLogByUUId(UUID uuid);
}