package com.example.investment_api.virtual.account.controller.dto;

public record OrderData(
        Long OrderId,
        String stockName,
        int remainCount,
        int stockCount,
        int buyPrice

) {
}
