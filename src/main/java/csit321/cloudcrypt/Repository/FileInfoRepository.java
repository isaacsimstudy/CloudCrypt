package csit321.cloudcrypt.Repository;

import csit321.cloudcrypt.Entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID> {
}