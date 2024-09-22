package com.example.investment_api.home.tradingVolume.controller.dto;

public record TradingVolumeDTO(
        String stockName,// 주식이름
        String rank, //순위
        String currentPrice, //현재가
        String totalVolume,//전체
        String prevVolume, //이전 거래량
        String volumeChangeRate //거래량
) {
}
