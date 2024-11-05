package com.example.investment_api.virtual.account.controller.dto;

public record BuyResponse(Long memberId, String stockName, int currentPrice, int quantity, int deposit) {

}
