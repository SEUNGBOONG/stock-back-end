package com.example.investment.home.fluctuation.service.client;

import com.example.investment.home.fluctuation.controller.dto.response.fluctuationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FluctuationDataFetcher {

    private final RestTemplate restTemplate;

    @Value("${}")
    private String appKey;

    @Value("${}")
    private String appSecret;

    @Value("${}")
    private String trId;

    @Value("${}")
    private String accessToken;

    public FluctuationDataFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public fluctuationResponse fluctuationData(String inputIscd) {
        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/ranking/fluctuation?fid_cond_mrkt_div_code=J&fid_cond_scr_div_code=20170&fid_input_iscd=0000&fid_rank_sort_cls_code=0&fid_input_cnt_1=0&fid_prc_cls_code=0&fid_input_price_1=&fid_input_price_2=&fid_vol_cnt=&fid_trgt_cls_code=0&fid_trgt_exls_cls_code=0&fid_div_cls_code=0&fid_rsfl_rate1=&fid_rsfl_rate2=\n";
        HttpHeaders headers = new HttpHeaders();
        headers.set("tr_id", trId);
        headers.set("appsecret", appSecret);
        headers.set("appkey", appKey);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<fluctuationResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, fluctuationResponse.class);
        return response.getBody();
    }
}
