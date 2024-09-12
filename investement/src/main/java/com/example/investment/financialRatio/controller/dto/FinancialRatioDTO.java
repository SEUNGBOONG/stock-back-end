package com.example.investment.financialRatio.controller.dto;

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
