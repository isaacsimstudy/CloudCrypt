package csit321.cloudcrypt.Service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.KeyRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.CloudService;
import csit321.cloudcrypt.Entity.Cloud;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import csit321.cloudcrypt.Repository.CloudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CloudServiceImpl implements CloudService {
    private final CloudRepository cloudRepository;
    private final KeyRepository keyRepository;
    private final UserAccountRepository userAccountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    public CloudServiceImpl(CloudRepository cloudRepository,
                            KeyRepository keyRepository,
                            UserAccountRepository userAccountRepository) {
        this.cloudRepository = cloudRepository;
        this.keyRepository = keyRepository;
        this.userAccountRepository = userAccountRepository;
    }


    @Override
    public String createCloud(UserAccount userAccount, String cloudName, String cloudFilePath, Key key, String cloudPath, String status) {
        Cloud cloud = new Cloud();
        cloud.setUserAccount(userAccount);
        cloud.setId(java.util.UUID.randomUUID());
        cloud.setFileName(cloudName);
        cloud.setFilePath(cloudFilePath);
        cloud.setKey(key);
        cloud.setStatus(status);
        cloud.setChecksum("0");
        cloudRepository.save(cloud);
        return "Success";
    }

    @Override
    public String readCloud(Map<String, String> param) {
        // Read cloud
        ArrayNode an = objectMapper.createArrayNode();
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(param.get("userAccount")).orElse(null);
        param.forEach((key, value) -> {
            switch (key) {
                case "cloudName" -> {
                    ObjectNode on = objectMapper.createObjectNode();
                    Cloud cloud = cloudRepository.findCloudByFileName(value).orElse(null);
                    if (cloud != null){
                        objectPut(an, cloud, on);
                    }
                }
                case "status" -> {
                    List<Cloud> cloudList = cloudRepository.findCloudByStatus(value);
                    for (Cloud cloud : cloudList) {
                        ObjectNode on = objectMapper.createObjectNode();
                        objectPut(an, cloud, on);
                    }
                }
                case "all" -> {
                    List<Cloud> cloudList = cloudRepository.findAllByUserAccount(userAccount);
                    for (Cloud cloud : cloudList) {
                        ObjectNode on = objectMapper.createObjectNode();
                        objectPut(an, cloud, on);
                    }
                }
            }
        });
        return an.toString();
    }

    private static void objectPut(ArrayNode an, Cloud cloud, ObjectNode on) {
        on.put("fileName", cloud.getFileName());
        on.put("filePath", cloud.getFilePath());
        on.put("key", cloud.getKey().getId().toString());
        on.put("status", cloud.getStatus());
        on.put("checksum", cloud.getChecksum());
        an.add(on);
    }

    @Override
    public String updateCloud(Map<String, String> param) {
        Cloud cloud = cloudRepository.findCloudByFileName(param.get("cloudName")).orElse(null);
        if (cloud == null) {
            return "Cloud not found";
        }
        param.forEach((key, value) -> {
            switch (key) {
                case "fileName" -> cloud.setFileName(value);
                case "filePath" -> cloud.setFilePath(value);
                case "key" -> cloud.setKey(keyRepository.findKeyById(java.util.UUID.fromString(value)));
                case "status" -> cloud.setStatus(value);
                case "checksum" -> cloud.setChecksum(value);
            }
        });
        cloudRepository.save(cloud);
        return "Success";
    }



    @Override
    public String deleteCloud(Map<String, String> param) {
        param.forEach((key, value) -> {
            switch (key) {
                case "cloudName" -> cloudRepository.deleteCloudByFileName(value);
                case "id" -> cloudRepository.deleteById(java.util.UUID.fromString(value));
                case "all" -> cloudRepository.deleteAll();
            }
        });
        return "Success";
    }

    // Push data from cloud to FileInfo
}
