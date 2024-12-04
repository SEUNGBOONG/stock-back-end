package com.example.investment_api.virtual.account.controller.dto;

public record AllResultDTO(
        String memberNickname, //멤버 닉네임
        int deposit, //예수금
        double totalEvaluationProfit, //총 평가손익
        double totalPurchaseAmount, //총 매입금액
        int totalEvaluationAmount, //총 평가금액
        int estimatedAsset, //추정 자산
        int rank //순위

) {
}