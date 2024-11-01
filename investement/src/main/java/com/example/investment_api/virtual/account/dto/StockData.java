package com.example.investment_api.virtual.account.dto;

public record StockData(
        String stockName,
        int currentPrice,
        Double prevChangeRate
){}
