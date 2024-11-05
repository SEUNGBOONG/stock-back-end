package com.example.investment_api.virtual.account.controller.dto;

public record SellResponse(
        Long memberId,
        String stockName,
        int currentPrice,
        int remainNumbers) {
}
