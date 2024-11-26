package com.example.investment_api.search.detail.chart.controller.dto;

public record ChartDTO(
        String date, // 날짜
        String endPrice, //종가
        String highPrice, //최고가
        String lowPrice, //최저가
        String prevPrice //전일 대비 가격 - 이 가격에 따라 봉 색깔 판정
) {
}
