package com.example.investment_api.search.detail.consensus.service.client;

import com.example.investment_api.common.token.TokenService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class InvestmentRecommendationFetcher {

    @Value("${CONSENSUS_TR_ID}")
    private String trId;

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Value("${API_APP_KEY}")
    private String appKey;

    private final TokenService tokenService;

    private final RestTemplate restTemplate;

    public InvestmentRecommendationFetcher(final TokenService tokenService, final RestTemplate restTemplate) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> investmentRecommendationData(String fid_input_iscd) {
        return getStringResponseEntity(fid_input_iscd);
    }

    private ResponseEntity<String> getStringResponseEntity(final String fid_input_iscd) {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/invest-opinion?"
                + "FID_COND_MRKT_DIV_CODE=J&FID_COND_SCR_DIV_CODE=16633"
                + "&FID_INPUT_DATE_1=20231113&FID_INPUT_DATE_2=20240513"
                + "&FID_INPUT_ISCD=" + fid_input_iscd;

        HttpHeaders headers = setHeader();

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("tr_id", trId);
        headers.set("appsecret", appSecret);
        headers.set("appkey", appKey);
        headers.set("Authorization", "Bearer " + tokenService.getAccessToken());
        return headers;
    }
}
