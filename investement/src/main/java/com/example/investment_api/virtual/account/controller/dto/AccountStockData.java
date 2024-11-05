package com.example.investment_api.virtual.account.controller.dto;

public record AccountStockData(
        String stockName,
        int buyPrice,
        int stockCount,
        int currentPrice,
        double prevChangeRate
) {
}
