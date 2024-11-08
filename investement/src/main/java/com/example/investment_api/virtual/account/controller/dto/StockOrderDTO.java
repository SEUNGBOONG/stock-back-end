package com.example.investment_api.virtual.account.controller.dto;


public record StockOrderDTO(
        String stockName,
        int quantity,
        int limitPrice
) {
}
