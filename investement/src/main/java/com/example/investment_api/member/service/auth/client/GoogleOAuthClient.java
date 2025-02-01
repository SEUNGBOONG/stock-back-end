package com.example.investment_api.member.service.auth.client;

import com.example.investment_api.member.domain.auth.OAuthClient;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthClient implements OAuthClient {

    private static final String AUTH_Url = "https://accounts.google.com/o/oauth2/auth";

    @Value("${GOOGLE_OAUTH_CLIENT_ID}")
    private String clientId;

    @Value("${GOOGLE_OAUTH_REDIRECTION_URL}")
    private String redirectUri;

    @Value("${GOOGLE_OAUTH_SCOPE}")
    private String scope;


    @Override
    public String getUrl() {
        return AUTH_Url + "?client_id=" + clientId +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                "&response_type=code" +
                "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) +
                "&access_type=offline";
    }
}
