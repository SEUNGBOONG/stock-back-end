package com.example.investment.index.service.client;

import com.example.investment.template.RestTemplateClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class IndexApiService {

    private final RestTemplateClient restTemplateClient;

    @Value("${serviceKey}")
    private String serviceKey;

    public IndexApiService(RestTemplateClient restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    public ResponseEntity<String> getKOSPIIndexData() {
        String url = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/getStockMarketIndex?serviceKey="
                + serviceKey + "&resultType=json&pageNo=1&numOfRows=1&idxNm=코스피";
        HttpHeaders headers = new HttpHeaders();
        return restTemplateClient.get(url, headers);
    }

    public ResponseEntity<String> getKOSDAQIndexData() {
        String url = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/getStockMarketIndex?serviceKey="
                + serviceKey + "&resultType=json&pageNo=1&numOfRows=1&idxNm=코스닥";
        HttpHeaders headers = new HttpHeaders();
        return restTemplateClient.get(url, headers);
    }
}
