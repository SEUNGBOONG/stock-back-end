package com.example.investment_api.search.consensus.controller;

import com.example.investment_api.search.consensus.controller.dto.InvestmentRecommendationDTO;

import com.example.investment_api.search.consensus.service.InvestmentRecommendationService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/search/investmentRecommendation")
public class InvestmentRecommendationController {

    private final InvestmentRecommendationService investmentRecommendationService;

    public InvestmentRecommendationController(final InvestmentRecommendationService investmentRecommendationService) {
        this.investmentRecommendationService = investmentRecommendationService;
    }

    @GetMapping
    @CrossOrigin(origins = {"http://13.209.4.56:8080", "http://localhost:3000"})
    public ResponseEntity<InvestmentRecommendationDTO> getFinancialRatio(@RequestParam String stockInfo) throws IOException {
        return getInvestmentRecommendationDTOResponseEntity(stockInfo);
    }

    private ResponseEntity<InvestmentRecommendationDTO> getInvestmentRecommendationDTOResponseEntity(final String stockInfo) throws IOException {
        InvestmentRecommendationDTO investmentRecommendationDTO = investmentRecommendationService.getInvestmentRecommendation(stockInfo);
        return ResponseEntity.ok(investmentRecommendationDTO);
    }
}
