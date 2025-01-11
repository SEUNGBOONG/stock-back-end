package com.example.investment_api.search.base.searchHome.controller.dto;

public record StockDataDTO(
        String rank,
        String stockName,
        String stockPrice,
        String prevChangePrice, //전일 대비가
        String prevChangeRate, //전일 대비율
        String marketCapitalization,
        String tradingVolume
) {
}
