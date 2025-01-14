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

    private static final String URL = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/ranking/market-cap?" +
            "fid_cond_mrkt_div_code=J&" +
            "fid_cond_scr_div_code=20174&" +
            "fid_div_cls_code=0&" +
            "fid_input_iscd=0000&" +
            "fid_trgt_cls_code=0&" +
            "fid_trgt_exls_cls_code=0&" +
            "fid_input_price_1=&" +
            "fid_input_price_2=&" +
            "fid_vol_cnt=";
    private static final String TR_ID = "tr_id";
    private static final String APPSECRET = "appsecret";
    private static final String APPKEY = "appkey";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final RestTemplate restTemplate;

    public MarketCapitalizationFetcher(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Value("${API_APP_KEY}")
    private String appKey;

    @Value("${API_ACCESS_TOKEN}")
    private String accessToken;

    @Value("${MARKET_TR_ID}")
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
        return URL;
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(TR_ID, trId);
        headers.set(APPSECRET, appSecret);
        headers.set(APPKEY, appKey);
        headers.set(AUTHORIZATION, BEARER + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
