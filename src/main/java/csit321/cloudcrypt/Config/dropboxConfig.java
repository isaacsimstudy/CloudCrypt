package csit321.cloudcrypt.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

@Configuration
public class dropboxConfig {
    //TODO: Add dropbox configuration

    @Value("${dropbox.access.token}")
    private static String ACCESS_TOKEN;

    @Value("${dropbox.app.key}")
    private String appKey;

    @Value("${dropbox.app.secret}")
    private String appSecret;

    public DbxClientV2 dbxClientV2() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("CloudCrypt/1.0").build();
        return new DbxClientV2(config, ACCESS_TOKEN);
    }




}
