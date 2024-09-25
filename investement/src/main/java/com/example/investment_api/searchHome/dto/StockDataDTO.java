package com.example.investment_api.searchHome.dto;

public record StockDataDTO(
        String rank,
        String stockName,
        String stockPrice,
        String prevChangePrice, //전일 대비가
        String prevSign, //전일 대비 부호
        String prevChangeRate, //전일 대비율
        String marketCapitalization,
        String tradingVolume
) {
}
