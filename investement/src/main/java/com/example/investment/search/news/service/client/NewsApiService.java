package com.example.investment.search.news.service.client;

import com.example.investment.common.RestTemplateClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NewsApiService {

    private final RestTemplateClient restTemplateClient;

    @Value("${naver.api.clientId}")
    private String clientId;

    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    public NewsApiService(RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    public ResponseEntity<String> get(String url, HttpHeaders headers) {
        setHeader(headers);
        return restTemplateClient.exchange(url, HttpMethod.GET, headers, String.class);
    }

    private void setHeader(final HttpHeaders headers) {
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
    }
}
