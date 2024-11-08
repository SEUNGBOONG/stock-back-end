package com.example.investment_api.portfolio.controller.dto;

import com.example.investment_api.portfolio.infrastructure.PortfolioType;

public record PortfolioResponse(int stocks,
                                int bonds,
                                int savings,
                                int realEstate,
                                PortfolioType portfolioType) {
}
