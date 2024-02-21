package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.Cloud;
import csit321.cloudcrypt.Entity.FileInfo;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface FileInfoService {
    // CRUD operations for FileInfo
    String createFileInfo(Cloud cloud, Long originalSize, String fileType, String originalHash, String encryptionType, UserAccount userAccount, String tags);

    String readFileInfo(Map<String, String> param);

    String updateFileInfo(Map<String, String> param);

    String deleteFileInfo(Map<String, String> param);
}
