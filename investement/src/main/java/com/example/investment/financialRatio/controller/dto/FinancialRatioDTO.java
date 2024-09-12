package com.example.investment.financialRatio.controller.dto;

public record FinancialRatioDTO(
        String stockAccountingYearMonth, //년도월일
        String grossMarginRatio, // 총매출원가
        String businessProfitRate, // 사업이익률
        String netInterestRate, // 순이자율
        String roeValue, //  자기자본이익률 ROE로표시
        String earningsPerShare,  //EPS 로표시 주당순이익
        String salesPerShare, //주당 매출 :SPS 라고 표시
        String bookValuePerShare, //  주당순자산가치 BPS
        String reserveRate,  //적립금 비율
        String liabilityRate // 부채비율
) {}
