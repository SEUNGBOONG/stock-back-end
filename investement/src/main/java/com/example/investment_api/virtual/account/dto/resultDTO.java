package com.example.investment_api.virtual.account.dto;

public record resultDTO(
        String stockName, //주식명
        int currentPrice, //현재가
        int stockCount, //보유 수량
        double EvaluationProfit, //평가손익
        double ProfitRate, //수익률
        int purchaseAmount, //매입 금액
        int EvaluationAmount //평가 금액
) {
}
