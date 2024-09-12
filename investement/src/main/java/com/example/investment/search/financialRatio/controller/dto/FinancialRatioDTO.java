package com.example.investment.search.financialRatio.controller.dto;

public record FinancialRatioDTO(
        String stockAccountingYearMonth,
        String grossMarginRatio,
        String businessProfitRate,
        String netInterestRate,
        String roeValue,
        String earningsPerShare,
        String salesPerShare,
        String bookValuePerShare,
        String reserveRate,
        String liabilityRate
) {}
