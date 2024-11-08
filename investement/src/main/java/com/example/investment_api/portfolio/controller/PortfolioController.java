package com.example.investment_api.portfolio.controller;

import com.example.investment_api.global.annotation.Member;
import com.example.investment_api.portfolio.controller.dto.PortfolioRequest;
import com.example.investment_api.portfolio.controller.dto.PortfolioResponse;

import com.example.investment_api.portfolio.service.PortfolioService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(final PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/recommend")
    public ResponseEntity<PortfolioResponse> getRecommendedPortfolio(@Member Long memberId) {
        PortfolioResponse portfolioResponse = portfolioService.getRecommendedPortfolio(memberId);
        return ResponseEntity.ok(portfolioResponse);
    }
}
