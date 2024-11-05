package com.example.investment_api.virtual.account.controller.dto;

public record BuyRequest(
        String stockName
        ,int quantity
) {
}
