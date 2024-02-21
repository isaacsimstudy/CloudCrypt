package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.Cloud;
import csit321.cloudcrypt.Entity.FileInfo;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID> {
    Optional<FileInfo> findFileInfoByIdAndUserAccount(UUID uuid, UserAccount userAccount);

    FileInfo findFileInfoByFileAndUserAccount(Cloud cloud, UserAccount userAccount);

    List<FileInfo> findFileInfoByUserAccount(UserAccount userAccount);
}