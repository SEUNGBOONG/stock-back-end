package com.example.investment.home.tradingVolume.controller.dto;

public record TradingVolumeDTO(
        String stockName,
        String rank,
        String currentPrice,
        String totalVolume,
        String prevVolume,
        String volumeChangeRate
) {
}
