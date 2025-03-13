package com.example.investment_api.home.index.service.client;

import com.example.investment_api.common.config.RestTemplateClient;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.stereotype.Component;

import org.springframework.http.ResponseEntity;

@Component
public class IndexFetcher {

    public static final String KOPSI_URL = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/getStockMarketIndex?serviceKey=";
    public static final String KOSDAQ_URL = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/getStockMarketIndex?serviceKey=";
    @Value("${SERVICEKEY}")
    private String serviceKey;

    private final RestTemplateClient restTemplateClient;

    public IndexFetcher(final RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    public ResponseEntity<String> getKOSPIIndexData() {
        return getResponseEntity();
    }

    private ResponseEntity<String> getResponseEntity() {
        String url = KOPSI_URL
                + serviceKey + "&resultType=json&pageNo=1&numOfRows=1&idxNm=코스피";
        HttpHeaders headers = new HttpHeaders();
        setURLHeaders result = new setURLHeaders(url, headers);
        return restTemplateClient.exchange(result.url(), HttpMethod.GET, result.headers(), null);
    }

    public ResponseEntity<String> getKOSDAQIndexData() {
        return getStringResponseEntity();
    }

    private ResponseEntity<String> getStringResponseEntity() {
        String url = KOSDAQ_URL
                + serviceKey + "&resultType=json&pageNo=1&numOfRows=1&idxNm=코스닥";
        HttpHeaders headers = new HttpHeaders();
        return restTemplateClient.exchange(url, HttpMethod.GET, headers, null);
    }

    private record setURLHeaders(String url, HttpHeaders headers) {
    }
}
