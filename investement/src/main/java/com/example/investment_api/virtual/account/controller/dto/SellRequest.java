package com.example.investment_api.virtual.account.controller.dto;

public record SellRequest(
        Long memberId,
        String stockName,
        int quantity
) {
}
