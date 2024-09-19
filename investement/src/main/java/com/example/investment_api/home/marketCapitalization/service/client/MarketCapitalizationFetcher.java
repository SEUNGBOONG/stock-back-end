package com.example.investment_api.home.marketCapitalization.service.client;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class MarketCapitalizationFetcher {

    private final RestTemplate restTemplate;

    public MarketCapitalizationFetcher(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${api.app_secret}")
    private String appSecret;

    @Value("${api.app_key}")
    private String appKey;

    @Value("${api.access_token}")
    private String accessToken;

    @Value("${marketCapitalization.tr_id}")
    private String trId;

    public ResponseEntity<String> marketCapitalizationData() {
        return getStringResponseEntity();
    }

    private ResponseEntity<String> getStringResponseEntity() {
        String url = setURL();
        HttpEntity<String> entity = getStringHttpEntity();
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    private HttpEntity<String> getStringHttpEntity() {
        HttpHeaders headers = setHeader();
        return new HttpEntity<>(headers);
    }

    private static String setURL() {
        return "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/ranking/market-cap?" +
                "fid_cond_mrkt_div_code=J&" +
                "fid_cond_scr_div_code=20174&" +
                "fid_div_cls_code=0&" +
                "fid_input_iscd=0000&" +
                "fid_trgt_cls_code=0&" +
                "fid_trgt_exls_cls_code=0&" +
                "fid_input_price_1=&" +
                "fid_input_price_2=&" +
                "fid_vol_cnt=";
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("tr_id", trId);
        headers.set("appsecret", appSecret);
        headers.set("appkey", appKey);
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
