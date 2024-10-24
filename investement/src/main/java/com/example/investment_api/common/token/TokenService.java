//package com.example.investment_api.common.token;
//
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.file.Paths;
//import java.util.Properties;
//
//@Service
//public class TokenService {
//
//    @Value("${API_APP_KEY}")
//    private String appKey;
//
//    @Value("${API_APP_SECRET}")
//    private String appSecret;
//
//    // 현재 access token을 가져오는 메서드
//    @Getter
//    @Value("${API_ACCESS_TOKEN}")
//    private String accessToken;
//
//    // application.properties 파일 경로 (필요에 따라 조정 가능)
//    private final String propertiesFilePath = "./src/main/resources/application.properties";
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    // 24시간마다 토큰을 갱신
//    @Scheduled(fixedRate = 86400000)
//    public void renewAccessToken() {
//        String url = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";
//
//        // 요청 바디에 appKey와 appSecret을 삽입
//        String requestBody = String.format("{\"grant_type\":\"client_credentials\",\"appkey\":\"%s\",\"appsecret\":\"%s\"}", appKey, appSecret);
//
//        // 토큰 발급 API 호출
//        TokenResponse tokenResponse = restTemplate.postForObject(url, requestBody, TokenResponse.class);
//
//        // 새로운 access token이 성공적으로 발급되었을 때만 파일에 반영
//        if (tokenResponse != null && tokenResponse.getAccessToken() != null) {
//            this.accessToken = tokenResponse.getAccessToken();
//            System.out.println("New access token: " + this.accessToken);
//            updateAccessTokenInPropertiesFile(this.accessToken);
//        } else {
//            System.out.println("Failed to renew access token.");
//        }
//    }
//
//    // application.properties 파일에 access token을 업데이트하는 메서드
//    private void updateAccessTokenInPropertiesFile(String newAccessToken) {
//        try {
//            // Properties 객체 생성 및 application.properties 파일 로드
//            Properties properties = new Properties();
//            properties.load(Paths.get(propertiesFilePath).toUri().toURL().openStream());
//
//            // 새로운 access token 설정
//            properties.setProperty("api.access_token", newAccessToken);
//
//            try (OutputStream out = new FileOutputStream(propertiesFilePath)) {
//                properties.store(out, null);
//            }
//
//            System.out.println("application.properties 파일이 성공적으로 업데이트되었습니다.");
//        } catch (IOException e) {
//            System.err.println("application.properties 파일을 업데이트하는 중에 오류가 발생했습니다: " + e.getMessage());
//        }
//    }
//
//    // TokenResponse 클래스 (응답 형식을 맞추어 작성)
//    public static class TokenResponse {
//        private String access_token;
//
//        public String getAccessToken() {
//            return access_token;
//        }
//
//        public void setAccessToken(String access_token) {
//            this.access_token = access_token;
//        }
//    }
//}
