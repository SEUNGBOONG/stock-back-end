package com.example.investment_api.search.detail.financialRatio.controller.dto;

public record FinancialRatioDTO(
        String stockAccountingYearMonth,  //날짜
        String grossMarginRatio, // 총마진율 즉 순수익률
        String businessProfitRate, //사업 수익률
        String netInterestRate, //순이자율
        String roeValue, // ROE
        String earningsPerShare, //EPS
        String salesPerShare, //SPS
        String bookValuePerShare, // 주당 순이익 EPS
        String reserveRate, // 주식유보율
        String liabilityRate //부채율
) {
}
