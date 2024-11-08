package com.example.investment_api.portfolio.controller.dto;

public record PortfolioRequest(Long memberId,
                               int annualIncome,
                               boolean propensity) {
}
