package com.example.investment_api.search.detail.consensus.controller.dto;

public record InvestmentRecommendationDTO(
        String avgTargetPrice, //펀드매니저들이 평가한 목표가
        String avgStockPrice, //현재가
        String avgStockDifferencePrice //목표가 대비 현재가 차이
) {
}
