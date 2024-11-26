package com.example.investment_api.search.detail.chart.service.client;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class MonthChartFetcher {

    @Value("${CHART_TR_ID}")
    private String trId;

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Value("${API_APP_KEY}")
    private String appKey;

    @Value("${API_ACCESS_TOKEN}")
    private String accessToken;

    private final RestTemplate restTemplate;

    public MonthChartFetcher(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getMonthChartData(String fid_input_iscd) {
        return getStringResponseEntity(fid_input_iscd);
    }

    private ResponseEntity<String> getStringResponseEntity(final String fid_input_iscd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(10000); // 2016.08.31까지 밖에 데이터를 제공을 안해줌
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedEndDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice?"
                + "FID_COND_MRKT_DIV_CODE=J&FID_COND_SCR_DIV_CODE=16633"
                + "&FID_INPUT_DATE_1=" + formattedStartDate // 100일 전 날짜
                + "&FID_INPUT_DATE_2=" + formattedEndDate // 현재 날짜
                + "&FID_PaERIOD_DIV_CODE=M&FID_ORG_ADJ_PRC=0"
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
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }
}
