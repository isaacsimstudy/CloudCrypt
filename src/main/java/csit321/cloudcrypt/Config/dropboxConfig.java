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

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

@Configuration
public class dropboxConfig {
    //TODO: Add dropbox configuration

    /* idk why but this line of code not working, instead I use the access_token on line 27
    This current active access token was generated on 14 Feb 2024 7:53pm.

    @Value("<Access_Token>")
    private static String ACCESS_TOKEN;
     */
    private static final String ACCESS_TOKEN = "sl.BvmrgbNClX5utoEsY0RlmBDG7zsY6NiAaS4-0LulvxQJuBWpdmM46EAdgctnYoaVx02zeI7dm17hkuZQrZoU_j1sp65aPKQKjQko9MSz_bUqX7G3lmyDoREmEJdnfqLxgOIwGrQEuP1i";

    @Value("${dropbox.app.key}")
    private String appKey;

    @Value("${dropbox.app.secret}")
    private String appSecret;

    public DbxClientV2 dbxClientV2() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("CloudCrypt/1.0").build();
        return new DbxClientV2(config, ACCESS_TOKEN);
    }

    public static void main(String[] args) throws DbxException, IOException {
        // use existing config to create a new client
        DbxClientV2 client = new dropboxConfig().dbxClientV2();

        // get current account info
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

        // get files and folder metadata from Dropbox root directory
        ListFolderResult result = client.files().listFolder("");
        while(true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }

        // attempt to upload file "test.txt" to Dropbox. This will fail if the file is not found or does not exist.
        /*
        try (InputStream in = new FileInputStream(("test.txt"))) {
            FileMetadata metadata = client.files().uploadBuilder("/test.txt").uploadAndFinish(in);
        }
        */


    }


}
