package csit321.cloudcrypt.Controller.Customer;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.util.Scanner;

@RestController
@RequestMapping("/Integration")
public class DropboxIntegrationController {

    private static final String DROPBOX_TOKEN_URL = "https://api.dropbox.com/oauth2/token";

    private WebClient webClient = WebClient.create("https://api.dropbox.com");

    @PostMapping("/Authorize")
    public Mono<RedirectView> AuthorizeDropbox() {
        // redirect to Dropbox authorization URL
        String authorizationUrl = "https://www.dropbox.com/oauth2/authorize?client_id=vint2uip4sqtcso&response_type=code&redirect_uri=REDIRECT_URI";

        return Mono.just(new RedirectView(authorizationUrl));
    }

    @PostMapping("/Callback")
    public Mono<String> callback(@RequestParam String code) {
        return exchangeCodeForAccessToken(code)
                .flatMap(accessToken -> {
                    // use access token to call Dropbox API

                    return Mono.just("Done!");
                });
    }

    private Mono<String> exchangeCodeForAccessToken(String code) {
        return webClient.method(HttpMethod.POST)
                .uri(DROPBOX_TOKEN_URL)
                .body(BodyInserters.fromFormData(getAccessTokenRequest(code)))
                .retrieve()
                .bodyToMono(String.class);
    }

    private MultiValueMap<String, String> getAccessTokenRequest(String code) {
        // build request
        return new LinkedMultiValueMap<>();
    }
    @PostMapping("/Token")
    public static String requestAccessToken(String authorizationCode, String redirectUri, String appKey, String appSecret) throws Exception {

        URL url = new URL("https://api.dropboxapi.com/oauth2/token");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String params = "code=" + authorizationCode +
                "&grant_type=authorization_code" +
                "&redirect_uri=" + redirectUri +
                "&client_id=" + appKey +
                "&client_secret=" + appSecret;

        try(OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())) {
            wr.write(params);
            wr.flush();
        }

        Scanner scanner = new Scanner(conn.getInputStream());

        return scanner.hasNext() ? scanner.nextLine() : "";

    }

    public static void main(String[] args) throws Exception {
        String token = requestAccessToken(
                "<AUTHORIZATION_CODE>",
                "<REDIRECT_URI>",
                "<APP_KEY>",
                "<APP_SECRET>");

        System.out.println(token);
    }

}
