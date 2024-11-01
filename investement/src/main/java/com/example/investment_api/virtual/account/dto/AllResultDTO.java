package com.example.investment_api.virtual.account.dto;

public record AllResultDTO(
        double totalEvaluationProfit, //총 평가손익
        double totalPurchaseAmount, //총 매입금액
        double totalProfit, //수익률
        int totalEvaluationAmount //총 평가금액
) {
}
