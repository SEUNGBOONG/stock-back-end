package com.example.investment.home.marketCapitalization.controller.dto;

public record MarketCapitalizationDTO(
        String rank, //순위
        String stockPrice, //주식가격
        String stockName, //주식이름
        String marketCapitalization
) {
}
