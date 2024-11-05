package com.example.investment_api.virtual.account.controller.dto;

public record StockData(
        String stockName,
        int currentPrice,
        Double prevChangeRate
) { }
