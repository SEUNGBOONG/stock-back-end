package com.example.investment_api.home.marketCapitalization.controller.dto;

public record MarketCapitalizationDTO(
        String rank, //순위
        String stockPrice, //주식가격
        String stockName, //주식이름
        String marketCapitalization //시가총액 단위 = 000조 000
) {
}
