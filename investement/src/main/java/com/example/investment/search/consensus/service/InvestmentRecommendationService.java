package com.example.investment.search.consensus.service;

import com.example.investment.search.consensus.controller.dto.InvestmentRecommendationDTO;

import com.example.investment.search.consensus.domain.InvestmentRecommendationParser;

import com.example.investment.search.consensus.service.client.InvestmentRecommendationFetcher;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Transactional
public class InvestmentRecommendationService {

    private final InvestmentRecommendationParser investmentRecommendationParser;
    private final InvestmentRecommendationFetcher investmentRecommendationFetcher;

    @Autowired
    public InvestmentRecommendationService(final InvestmentRecommendationParser investmentRecommendationParser, final InvestmentRecommendationFetcher investmentRecommendationFetcher) {
        this.investmentRecommendationParser = investmentRecommendationParser;
        this.investmentRecommendationFetcher = investmentRecommendationFetcher;
    }
    public InvestmentRecommendationDTO getInvestmentRecommendation(String stockInfo) throws IOException {
        return getInvestmentRecommendationDTO(stockInfo);
    }

    private InvestmentRecommendationDTO getInvestmentRecommendationDTO(final String stockInfo) throws IOException {
        ResponseEntity<String> response = investmentRecommendationFetcher.investmentRecommendationData(stockInfo);
        return investmentRecommendationParser.parseInvestmentRecommendation(response.getBody());
    }

}
