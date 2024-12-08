package com.example.investment_api.portfolio.controller;

import com.example.investment_api.global.annotation.Member;
import com.example.investment_api.portfolio.controller.dto.PortfolioResponse;

import com.example.investment_api.portfolio.service.PortfolioService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(final PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<PortfolioResponse>> getRecommendedPortfolio(@Member Long memberId) {
        List<PortfolioResponse> portfolioResponse = portfolioService.getRecommendedPortfolio(memberId);
        return ResponseEntity.ok(portfolioResponse);
    }
}
