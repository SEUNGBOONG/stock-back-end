package com.example.investment.news.service.client;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsFetcher {

    private final ApiService apiService;

    @Value("${naver.api.clientId}")
    private String clientId;

    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    public NewsFetcher(ApiService apiService) {
        this.apiService = apiService;
    }

    public ResponseEntity<String> fetch(String keyword) throws JSONException {
        String url = "https://openapi.naver.com/v1/search/news.json?query=" + keyword;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        return apiService.get(url, headers);
    }
}
