package com.example.investment.financialRatio.service.client;

import com.example.investment.financialRatio.controller.dto.FinancialRatioResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FinancialRatioDataFetcher {

    private final RestTemplate restTemplate;

    @Value("${api.tr_id}")
    private String trId;

    @Value("${api.app_secret}")
    private String appSecret;

    @Value("${api.app_key}")
    private String appKey;

    @Value("${api.access_token}")
    private String accessToken;

    public FinancialRatioDataFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FinancialRatioResponse fetchFinancialRatioData(String fidInputIscd) {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/finance/financial-ratio?FID_DIV_CLS_CODE=0&fid_cond_mrkt_div_code=J&fid_input_iscd=" + fidInputIscd;
        HttpHeaders headers = new HttpHeaders();
        headers.set("tr_id", trId);
        headers.set("appsecret", appSecret);
        headers.set("appkey", appKey);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<FinancialRatioResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, FinancialRatioResponse.class);

        return response.getBody();
    }
}
