package csit321.cloudcrypt.Service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import csit321.cloudcrypt.Entity.ActivityLog;
import csit321.cloudcrypt.Entity.Key;
import csit321.cloudcrypt.Entity.NotificationSetting;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Repository.KeyRepository;
import csit321.cloudcrypt.Repository.UserAccountRepository;
import csit321.cloudcrypt.Service.KeyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class KeyServiceImpl implements KeyService {
    private final KeyRepository KeyRepository;

    private final UserAccountRepository userAccountRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public KeyServiceImpl(KeyRepository KeyRepository, UserAccountRepository userAccountRepository) {
        this.KeyRepository = KeyRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public String getKey(UserAccount userAccount) {
        List<Key> KeyList = KeyRepository.findAllByUserAccount(userAccount);

        ArrayNode an = objectMapper.createArrayNode();
        for (Key Key : KeyList) {
            ObjectNode on = objectMapper.createObjectNode();
            on.put("key_id", String.valueOf(Key.getId()));
            on.put("name", Key.getName());
            on.put("password_hash", Key.getPassword_hash());
            an.add(on);
        }
        return an.toString();
    }

    @Override
    public String generateKey(UserAccount userAccount,
                              String name,
                              String password) {
        Key key = new Key();
        key.setId(UUID.randomUUID());
        key.setUserAccount(userAccount);
        key.setName(name);
        key.setPassword_hash(password);
        KeyRepository.save(key);
        return "New Key Generated.";
    }

    @Override
    public String deleteKey(Key key) {
        KeyRepository.delete(key);
        return "Key deleted.";
    }
}
