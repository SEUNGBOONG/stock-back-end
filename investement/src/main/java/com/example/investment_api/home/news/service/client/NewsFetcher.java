package com.example.investment_api.home.news.service.client;

import com.example.investment_api.common.config.RestTemplateClient;

import org.json.JSONException;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

@Component
public class NewsFetcher {

    private static final String X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    private static final String X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";
    private static final String NAVER_URL = "https://openapi.naver.com/v1/search/news.json?query=";

    @Value("${NAVER_API_CLIENTID}")
    private String clientId;

    @Value("${NAVER_API_KEY}")
    private String clientSecret;

    private final RestTemplateClient restTemplateClient;

    public NewsFetcher(final RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    public ResponseEntity<String> fetch(String keyword) throws JSONException {
        return setURL(keyword);
    }

    private ResponseEntity<String> setURL(final String keyword) {
        String url = NAVER_URL + keyword;
        HttpHeaders headers = setHeader();
        return get(url, headers);
    }

    public ResponseEntity<String> get(String url, HttpHeaders headers) {
        return restTemplateClient.exchange(url, HttpMethod.GET, headers, String.class);
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(X_NAVER_CLIENT_ID, clientId);
        headers.set(X_NAVER_CLIENT_SECRET, clientSecret);
        return headers;
    }
}
