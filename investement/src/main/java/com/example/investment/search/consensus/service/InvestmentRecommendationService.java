package com.example.investment.search.consensus.service;

import com.example.investment.search.consensus.controller.dto.InvestmentRecommendationDTO;
import com.example.investment.search.consensus.infrastructure.InvestmentRecommendationParser;
import com.example.investment.search.consensus.service.client.InvestmentRecommendationFetcher;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Transactional
public class InvestmentRecommendationService {

    private final InvestmentRecommendationParser investmentRecommendationParser;
    private final InvestmentRecommendationFetcher investmentRecommendationFetcher;

    public InvestmentRecommendationService(final InvestmentRecommendationParser investmentRecommendationParser, final InvestmentRecommendationFetcher investmentRecommendationFetcher) {
        this.investmentRecommendationParser = investmentRecommendationParser;
        this.investmentRecommendationFetcher = investmentRecommendationFetcher;
    }
    public InvestmentRecommendationDTO getInvestmentRecommendation(String fidInputIscd) throws IOException {
        ResponseEntity<String> response = investmentRecommendationFetcher.investmentRecommendationData(fidInputIscd);
        return investmentRecommendationParser.parseInvestmentRecommendation(response.getBody());
    }

}
