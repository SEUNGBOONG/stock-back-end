package com.example.investment.home.tradingVolume.service.client;

import com.example.investment.common.KISSetHeader;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class TradingVolumeFetcher {

    private final RestTemplate restTemplate;
    private final KISSetHeader kisSetHeader;

    public TradingVolumeFetcher(final RestTemplate restTemplate, final KISSetHeader kisSetHeader) {
        this.restTemplate = restTemplate;
        this.kisSetHeader = kisSetHeader;
    }

    public ResponseEntity<String> fetchTradingVolumeData() {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/volume-rank?FID_COND_MRKT_DIV_CODE=J&FID_COND_SCR_DIV_CODE=20171&FID_INPUT_ISCD=0000&FID_DIV_CLS_CODE=0&FID_BLNG_CLS_CODE=0&FID_TRGT_CLS_CODE=111111111&FID_TRGT_EXLS_CLS_CODE=0000000000&FID_INPUT_PRICE_1=0&FID_INPUT_PRICE_2=0&FID_VOL_CNT=0&FID_INPUT_DATE_1=0";
        HttpHeaders headers = kisSetHeader.setHeader();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

}
