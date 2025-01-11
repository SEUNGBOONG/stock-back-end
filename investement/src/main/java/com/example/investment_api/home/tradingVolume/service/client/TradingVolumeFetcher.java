package com.example.investment_api.home.tradingVolume.service.client;

import com.example.investment_api.common.api.ApiMessage;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class TradingVolumeFetcher {

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Value("${API_APP_KEY}")
    private String appKey;

    @Value("${API_ACCESS_TOKEN}")
    private String accessToken;

    @Value("${API_TRID}")
    private String trId;

    private final RestTemplate restTemplate;

    public TradingVolumeFetcher(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> fetchTradingVolumeData() {
        return getStringResponseEntity();
    }

    private ResponseEntity<String> getStringResponseEntity() {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/volume-rank?FID_COND_MRKT_DIV_CODE=J&FID_COND_SCR_DIV_CODE=20171&FID_INPUT_ISCD=0000&FID_DIV_CLS_CODE=0&FID_BLNG_CLS_CODE=0&FID_TRGT_CLS_CODE=111111111&FID_TRGT_EXLS_CLS_CODE=0000000000&FID_INPUT_PRICE_1=0&FID_INPUT_PRICE_2=0&FID_VOL_CNT=0&FID_INPUT_DATE_1=0";
        HttpHeaders headers = new HttpHeaders();
        headers.set(ApiMessage.TR_ID.name(), trId);
        headers.set(ApiMessage.APP_SECRET.name(), appSecret);
        headers.set(ApiMessage.APP_KEY.name(), appKey);
        headers.set(ApiMessage.AUTHORIZATION.name(), ApiMessage.BEARER + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

}
