package com.example.investment.home.index.service.client;

import com.example.investment.common.RestTemplateClient;
import com.example.investment.home.index.controller.dto.KOSDAQResponse;
import com.example.investment.home.index.controller.dto.KOSPIResponse;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;

import org.json.JSONObject;

@Component
public class IndexFetcher {

    @Value("${serviceKey}")
    private String serviceKey;

    private final RestTemplateClient restTemplateClient;

    public IndexFetcher(final RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    public ResponseEntity<String> getKOSPIIndexData() {
        String url = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/getStockMarketIndex?serviceKey="
                + serviceKey + "&resultType=json&pageNo=1&numOfRows=1&idxNm=코스피";
        HttpHeaders headers = new HttpHeaders();
        return restTemplateClient.exchange(url, HttpMethod.GET, headers, null);
    }

    public ResponseEntity<String> getKOSDAQIndexData() {
        String url = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/getStockMarketIndex?serviceKey="
                + serviceKey + "&resultType=json&pageNo=1&numOfRows=1&idxNm=코스닥";
        HttpHeaders headers = new HttpHeaders();
        return restTemplateClient.exchange(url, HttpMethod.GET, headers, null);
    }

}
