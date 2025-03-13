package com.example.investment_api.common.token;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    public static String TokenResponse;

    @Value("${API_APP_KEY}")
    private String appKey;

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Getter
    private String accessToken;

    private final RestTemplate restTemplate = new RestTemplate();

    // API 응답을 받을 DTO 클래스
    @Getter
    @Setter
    public static class TokenResponseDTO {
        private String access_token;
        private String token_type;
        private String expires_in;
    }

    // 24시간마다 토큰 갱신
    @Scheduled(fixedRate = 86400000) // 24시간마다 실행
    public void renewAccessToken() {
        String url = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";

        // 요청 본문
        String requestBody = String.format("{\"grant_type\":\"client_credentials\",\"appkey\":\"%s\",\"appsecret\":\"%s\"}", appKey, appSecret);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity 생성 (헤더와 본문 포함)
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // API 요청 보내기
        try {
            ResponseEntity<TokenResponseDTO> response = restTemplate.exchange(url, HttpMethod.POST, entity, TokenResponseDTO.class);
            TokenResponseDTO tokenResponseDTO = response.getBody();

            if (tokenResponseDTO != null && tokenResponseDTO.getAccess_token() != null) {
                this.accessToken = tokenResponseDTO.getAccess_token();
                System.out.println("새로운 토큰 발급 성공: " + this.accessToken);
            } else {
                System.out.println("토큰 발급 실패");
            }
        } catch (Exception e) {
            System.out.println("토큰 갱신 중 오류 발생: " + e.getMessage());
        }
    }
}
