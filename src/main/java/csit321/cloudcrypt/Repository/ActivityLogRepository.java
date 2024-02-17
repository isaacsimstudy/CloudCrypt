package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.ActivityLog;
import csit321.cloudcrypt.Entity.Cloud;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, UUID> {
    List<ActivityLog> findActivityLogByUserAccount(UserAccount userAccount);

    List<ActivityLog> findActivityLogByUserAccountAndActivityType(UserAccount userAccount, String param);

    List<ActivityLog> findActivityLogByUserAccountAndStatus(UserAccount userAccount, String param);

    List<ActivityLog> findActivityLogByUserAccountAndCloud(UserAccount userAccount, Cloud cloudFileID);

    ActivityLog findActivityLogById(UUID activityID);
}