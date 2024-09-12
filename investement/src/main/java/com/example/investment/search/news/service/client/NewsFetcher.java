package com.example.investment.search.news.service.client;

import com.example.investment.common.RestTemplateClient;
import org.json.JSONException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsFetcher {

    private final RestTemplateClient restTemplateClient;

    @Value("${naver.api.clientId}")
    private String clientId;

    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    public NewsFetcher(final RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    public ResponseEntity<String> fetch(String keyword) throws JSONException {
        String url = "https://openapi.naver.com/v1/search/news.json?query=" + keyword;
        HttpHeaders headers = setHeader();
        return get(url, headers);
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        return headers;
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
