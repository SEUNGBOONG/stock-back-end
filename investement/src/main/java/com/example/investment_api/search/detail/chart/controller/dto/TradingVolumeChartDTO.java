package com.example.investment_api.search.detail.chart.controller.dto;

public record TradingVolumeChartDTO(
        String date,
        String cumulativeVolume, //누적 거래량
        String changeDirection //증가, 감소
) {
}
