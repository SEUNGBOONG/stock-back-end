package com.example.investment_api.common.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    @Value("${API_ACCESS_TOKEN}")
    private String accessToken;

    @Value("${API_APP_KEY}")
    private String appKey;

    @Value("${API_APP_SECRET}")
    private String appSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken() {
        if (accessToken == null || accessToken.isEmpty()) {
            renewAccessToken();
        }
        return accessToken;
    }

    public void renewAccessToken() {
        String url = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";

        String requestBody = String.format("{\"grant_type\":\"client_credentials\",\"appkey\":\"%s\",\"appsecret\":\"%s\"}", appKey, appSecret);

        this.accessToken = restTemplate.postForObject(url, requestBody, String.class);
    }

}
