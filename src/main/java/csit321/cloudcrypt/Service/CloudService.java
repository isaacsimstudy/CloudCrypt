package csit321.cloudcrypt.Service;

import csit321.cloudcrypt.Entity.Cloud;
import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CloudService {

    // CRUD operations for Cloud

    public String createCloud(UserAccount userAccount, String cloudName, String cloudFilePath, Key key, String cloudPath, String status);

    public String readCloud(Map<String, String> param);

    public String updateCloud(Map<String, String> param);

    public String deleteCloud(Map<String, String> param);

    // Push data from cloud to FileInfo


}
