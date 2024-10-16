package com.example.investment_api.virtual.account.dto;

public record StockCalculationDTO(
        String stockName,
        int buyPrice,
        int stockCount,
        int currentPrice
) {
}
