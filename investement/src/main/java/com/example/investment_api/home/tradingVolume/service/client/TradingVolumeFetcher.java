package com.example.investment_api.home.tradingVolume.service.client;

import com.example.investment_api.common.token.TokenService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class TradingVolumeFetcher {

    private static final String URL = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/volume-rank?FID_COND_MRKT_DIV_CODE=J&FID_COND_SCR_DIV_CODE=20171&FID_INPUT_ISCD=0000&FID_DIV_CLS_CODE=0&FID_BLNG_CLS_CODE=0&FID_TRGT_CLS_CODE=111111111&FID_TRGT_EXLS_CLS_CODE=0000000000&FID_INPUT_PRICE_1=0&FID_INPUT_PRICE_2=0&FID_VOL_CNT=0&FID_INPUT_DATE_1=0";
    private static final String TR_ID = "tr_id";
    private static final String APPSECRET = "appsecret";
    private static final String APPKEY = "appkey";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final TokenService tokenService;

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Value("${API_APP_KEY}")
    private String appKey;


    @Value("${API_TRID}")
    private String trId;

    private final RestTemplate restTemplate;

    public TradingVolumeFetcher(final TokenService tokenService, final RestTemplate restTemplate) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> fetchTradingVolumeData() {
        return getStringResponseEntity();
    }

    private ResponseEntity<String> getStringResponseEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(TR_ID, trId);
        headers.set(APPSECRET, appSecret);
        headers.set(APPKEY, appKey);
        headers.set(AUTHORIZATION, BEARER + tokenService.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
    }

}
