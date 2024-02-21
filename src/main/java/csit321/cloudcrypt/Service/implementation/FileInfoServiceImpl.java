package csit321.cloudcrypt.Service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.Cloud;
import csit321.cloudcrypt.Entity.FileInfo;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.FileInfoRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

@Service
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;

    private final UserAccountRepository userAccountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository, UserAccountRepository userAccountRepository) {
        this.fileInfoRepository = fileInfoRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public String createFileInfo(Cloud cloud, Long originalSize, String fileType, String originalHash, String encryptionType, UserAccount userAccount, String tags) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(java.util.UUID.randomUUID());
        fileInfo.setFile(cloud);
        fileInfo.setUserAccount(userAccount);
        fileInfo.setOriginalSize(originalSize);
        fileInfo.setFileType(fileType);
        fileInfo.setOriginalHash(originalHash);
        fileInfo.setTimeUploaded(java.time.OffsetDateTime.now());
        fileInfo.setLastModified(java.time.OffsetDateTime.now());
        fileInfo.setEncryptionType(encryptionType);
        fileInfo.setTags(tags);
        fileInfoRepository.save(fileInfo);
        return "Success";
    }

    @Override
    public String readFileInfo(Map<String, String> param) {
        // Read file info
        ArrayNode an = objectMapper.createArrayNode();
        UserAccount userAccount = userAccountRepository.findUserAccountByUsername(param.get("userAccount")).orElse(null);
        param.forEach((key, value) -> {
            switch (key) {
                case "fileLogId" -> {
                    ObjectNode on = objectMapper.createObjectNode();
                    FileInfo fileInfo = fileInfoRepository.findFileInfoByIdAndUserAccount(java.util.UUID.fromString(value), userAccount).orElse(null);
                    objectPut(an, fileInfo, on);
                }
                case "cloudId" -> {
                    Cloud cloud = new Cloud();
                    cloud.setId(java.util.UUID.fromString(value));
                    FileInfo fileInfo = fileInfoRepository.findFileInfoByFileAndUserAccount(cloud, userAccount);
                    objectPut(an, fileInfo, objectMapper.createObjectNode());
                }
                case "userAccount" -> {
                    List<FileInfo> fileInfoList = fileInfoRepository.findFileInfoByUserAccount(userAccount);
                    for (FileInfo fileInfo : fileInfoList) {
                        ObjectNode on = objectMapper.createObjectNode();
                        objectPut(an, fileInfo, on);
                    }
                }
            }
        });
        return an.toString();
    }

    private static void objectPut(ArrayNode an, FileInfo fileInfo, ObjectNode on) {
        on.put("id", fileInfo.getId().toString());
        on.put("file", fileInfo.getFile().toString());
        on.put("userAccount", fileInfo.getUserAccount().getUsername());
        on.put("originalSize", fileInfo.getOriginalSize());
        on.put("fileType", fileInfo.getFileType());
        on.put("originalHash", fileInfo.getOriginalHash());
        on.put("timeUploaded", fileInfo.getTimeUploaded().toString());
        on.put("lastModified", fileInfo.getLastModified().toString());
        on.put("encryptionType", fileInfo.getEncryptionType());
        on.put("tags", fileInfo.getTags());
        an.add(on);
    }

    @Override
    public String updateFileInfo(Map<String, String> param) {
        FileInfo fileInfo = fileInfoRepository.findFileInfoByIdAndUserAccount(java.util.UUID.fromString(param.get("fileLogId")), userAccountRepository.findUserAccountByUsername(param.get("userAccount")).orElse(null)).orElse(null);
        if (fileInfo == null) {
            return "File info not found";
        }
        param.forEach((key, value) -> {
            switch (key) {
                case "fileLogId" -> fileInfo.setId(java.util.UUID.fromString(value));
                case "file" -> fileInfo.setFile(new Cloud());
                case "userAccount" -> fileInfo.setUserAccount(userAccountRepository.findUserAccountByUsername(value).orElse(null));
                case "originalSize" -> fileInfo.setOriginalSize(Long.parseLong(value));
                case "fileType" -> fileInfo.setFileType(value);
                case "originalHash" -> fileInfo.setOriginalHash(value);
                case "timeUploaded" -> fileInfo.setTimeUploaded(java.time.OffsetDateTime.parse(value));
                case "lastModified" -> fileInfo.setLastModified(java.time.OffsetDateTime.parse(value));
                case "encryptionType" -> fileInfo.setEncryptionType(value);
                case "tags" -> fileInfo.setTags(value);
            }
        });
        fileInfoRepository.save(fileInfo);
        return "Success";
    }

    @Override
    public String deleteFileInfo(Map<String, String> param) {
        param.forEach((key, value) -> {
            switch (key) {
                case "fileLogId" -> fileInfoRepository.deleteById(java.util.UUID.fromString(value));
                case "file" -> fileInfoRepository.delete(fileInfoRepository.findFileInfoByFileAndUserAccount(new Cloud(), userAccountRepository.findUserAccountByUsername(value).orElse(null)));
                case "userAccount" -> fileInfoRepository.deleteAll(fileInfoRepository.findFileInfoByUserAccount(userAccountRepository.findUserAccountByUsername(value).orElse(null)));
            }
        });
        return "Success";
    }
}
