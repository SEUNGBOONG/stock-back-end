package com.example.investment_api.search.detail.enterpriseInfo.controller.dto;

public record EnterpriseDTO(
        String industry, //산업 종류
        String marketCapitalization,//시가총액
        String major //종목 분류
) {
}
