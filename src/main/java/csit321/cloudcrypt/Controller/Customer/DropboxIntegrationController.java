package csit321.cloudcrypt.Controller.Customer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path ="/Integration")
public class DropboxIntegrationController {

    private static String DROPBOX_AUTHCODE_URL = "https://www.dropbox.com/oauth2/authorize?client_id=vint2uip4sqtcso&response_type=code&redirect_uri=http://localhost:63342/CloudCrypt/src/main/resources/static/Customer/CustomerIntegration.html";
    // http://localhost:63342/CloudCrypt/src/main/resources/static/Customer/CustomerIntegration.html
    private static String DROPBOX_TOKEN_URL = "https://api.dropbox.com/oauth2/token";

    /*
    public static URI getAuthURI() {
        // redirect to Dropbox authorization URL
        try {
            return URI.create(DROPBOX_AUTHCODE_URL);
        } catch (IllegalArgumentException err) {
            throw new IllegalArgumentException("Invalid URL: " + err.getMessage());
        }
    }*/

    @GetMapping(path = "/Authorize")
    public ResponseEntity<String> authorizationCode() {
        try {
            String url = DROPBOX_AUTHCODE_URL; // Simplified for brevity
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }




}
