package com.example.investment_api.common.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    @Value("${api.access_token}")
    private String accessToken;

    @Value("${api.appkey}")
    private String appKey;

    @Value("${api.appsecret}")
    private String appSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 86400000)
    public void renewAccessToken() {
        String url = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";

        String requestBody = String.format("{\"grant_type\":\"client_credentials\",\"appkey\":\"%s\",\"appsecret\":\"%s\"}", appKey, appSecret);

        this.accessToken = restTemplate.postForObject(url, requestBody, String.class);

    }
}
