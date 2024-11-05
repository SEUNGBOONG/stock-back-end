package com.example.investment_api.virtual.account.controller.dto;

public record SellRequest(
        String stockName,
        int quantity
) {
}
