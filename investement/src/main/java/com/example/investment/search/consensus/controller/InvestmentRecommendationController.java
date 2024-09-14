package com.example.investment.search.consensus.controller;

import com.example.investment.search.consensus.controller.dto.InvestmentRecommendationDTO;
import com.example.investment.search.consensus.service.InvestmentRecommendationService;

import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<InvestmentRecommendationDTO> getFinancialRatio(@RequestParam String fid_input_iscd) throws IOException {
        return getInvestmentRecommendationDTOResponseEntity(fid_input_iscd);
    }

    private ResponseEntity<InvestmentRecommendationDTO> getInvestmentRecommendationDTOResponseEntity(final String fid_input_iscd) throws IOException {
        InvestmentRecommendationDTO investmentRecommendationDTO = investmentRecommendationService.getInvestmentRecommendation(fid_input_iscd);
        return ResponseEntity.ok(investmentRecommendationDTO);
    }
}
