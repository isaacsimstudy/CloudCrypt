package csit321.cloudcrypt.Service;
import csit321.cloudcrypt.Entity.UserAccount;
import csit321.cloudcrypt.Entity.Key;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface KeyService {
    public String getKey(UserAccount userAccount);

    public String generateKey(UserAccount userAccount,
                                     String name,
                                     String password);

    String deleteKey(Key key);

    String downloadKey(Key key);
}
