package com.example.investment_api.virtual.account.dto;

public record AccountDataDTO(
        String stockName,
        int currentPrice,
        Double prevChangeRate
){}
