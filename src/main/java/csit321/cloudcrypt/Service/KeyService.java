package csit321.cloudcrypt.Service;
import csit321.cloudcrypt.Entity.UserAccount;
import org.springframework.stereotype.Service;
@Service
public interface KeyService {
    public String getKey(UserAccount userAccount);

}
