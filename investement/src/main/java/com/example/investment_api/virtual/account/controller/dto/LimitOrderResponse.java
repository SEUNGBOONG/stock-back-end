package com.example.investment_api.virtual.account.controller.dto;

public record LimitOrderResponse(Long memberId, String stockName, int limitPrice, int quantity) {
}
