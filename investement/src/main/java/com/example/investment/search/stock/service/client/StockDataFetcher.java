package com.example.investment.search.stock.service.client;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StockDataFetcher {

    private final RestTemplate restTemplate;

    public StockDataFetcher(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${stock.tr_id}")
    private String trId;

    @Value("${api.app_secret}")
    private String appSecret;

    @Value("${api.app_key}")
    private String appKey;

    @Value("${api.access_token}")
    private String accessToken;

    public ResponseEntity<String> fetchStockData(String stockInfo) {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price?FID_COND_MRKT_DIV_CODE=J" +
                "&FID_INPUT_ISCD=" + stockInfo;

        HttpHeaders headers = new HttpHeaders();
        headers.set("tr_id", trId);
        headers.set("appsecret", appSecret);
        headers.set("appkey", appKey);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

}
