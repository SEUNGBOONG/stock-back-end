package com.example.investment_api.common.token;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    @Value("${API_APP_KEY}")
    private String appKey;

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Getter
    private String accessToken;

    private final RestTemplate restTemplate = new RestTemplate();

    // 24시간마다 토큰을 갱신
    @Scheduled(fixedRate = 86400000)
    public void renewAccessToken() {
        String url = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";

        String requestBody = String.format("{\"grant_type\":\"client_credentials\",\"appkey\":\"%s\",\"appsecret\":\"%s\"}", appKey, appSecret);

        TokenResponse tokenResponse = restTemplate.postForObject(url, requestBody, TokenResponse.class);

        if (tokenResponse != null && tokenResponse.getAccessToken() != null) {
            this.accessToken = tokenResponse.getAccessToken();
            System.out.println("새로운 토큰 발급 성공  " + this.accessToken);
        } else {
            System.out.println("토큰 발급 실패");
        }
    }

    public static class TokenResponse {

        private String access_token;

        public String getAccessToken() {
            return access_token;
        }
    }
}
