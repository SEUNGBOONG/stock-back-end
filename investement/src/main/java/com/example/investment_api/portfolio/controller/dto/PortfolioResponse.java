package com.example.investment_api.portfolio.controller.dto;

public record PortfolioResponse(int stocks,
                                int bonds,
                                int savings,
                                int realEstate,
                                String portfolioType) {
}
