package com.example.investment_api.search.detail.enterpriseInfo.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EnterpriseFetcher {

    public static final String KOSPIProductType = "300";
    @Value("${ENTERPRISE_TR_ID}")
    private String trId;

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Value("${API_APP_KEY}")
    private String appKey;

    @Value("${API_ACCESS_TOKEN}")
    private String accessToken;

    private final RestTemplate restTemplate;

    public EnterpriseFetcher(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> fetchEnterpriseData(String stockName) {
        return setURL(stockName);
    }

    private ResponseEntity<String> setURL(final String stockName) {
        String url = String.format("https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/search-stock-info?PDNO=%s&PRDT_TYPE_CD=%s", stockName, KOSPIProductType);
        HttpHeaders headers = setHeader();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("tr_id", trId);
        headers.set("appsecret", appSecret);
        headers.set("appkey", appKey);
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
