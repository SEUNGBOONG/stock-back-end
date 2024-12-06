package com.example.investment_api.portfolio.controller.dto;

public record PortfolioResponse(
        String type,
        int stocks,
        int bonds,
        int savings,
        int realEstate,
        String description
) {
}
