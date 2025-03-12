package com.example.investment_api.search.detail.chart.service.client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.investment_api.common.token.TokenService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

@Component
public class DayChartFetcher {

    @Value("${CHART_TR_ID}")
    private String trId;

    @Value("${API_APP_SECRET}")
    private String appSecret;

    @Value("${API_APP_KEY}")
    private String appKey;

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public DayChartFetcher(final RestTemplate restTemplate, final TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    public ResponseEntity<String> getDayChartData(String fid_input_iscd) {
        return getStringResponseEntity(fid_input_iscd);
    }

    private ResponseEntity<String> getStringResponseEntity(final String fid_input_iscd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(100000); // 100일 전 날짜 얘는 2024년 7월 1일까지 밖에 데이터를 안준다.
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedEndDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice?"
                + "FID_COND_MRKT_DIV_CODE=J&FID_COND_SCR_DIV_CODE=16633"
                + "&FID_INPUT_DATE_1=" + formattedStartDate // 100일 전 날짜
                + "&FID_INPUT_DATE_2=" + formattedEndDate // 현재 날짜
                + "&FID_PERIOD_DIV_CODE=D&FID_ORG_ADJ_PRC=0"
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
