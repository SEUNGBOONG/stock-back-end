package com.example.investment_api.search.detail.consensus.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;

import com.example.investment_api.search.detail.consensus.controller.dto.InvestmentRecommendationDTO;

import com.example.investment_api.search.detail.consensus.domain.InvestmentRecommendationParser;

import com.example.investment_api.search.detail.consensus.service.client.InvestmentRecommendationFetcher;

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

    private final StockRepository stockRepository;

    @Autowired
    public InvestmentRecommendationService(final InvestmentRecommendationParser investmentRecommendationParser, final InvestmentRecommendationFetcher investmentRecommendationFetcher, final StockRepository stockRepository) {
        this.investmentRecommendationParser = investmentRecommendationParser;
        this.investmentRecommendationFetcher = investmentRecommendationFetcher;
        this.stockRepository = stockRepository;
    }

    public InvestmentRecommendationDTO getInvestmentRecommendation(String stockName) throws IOException {
        return getInvestmentRecommendationDTO(stockName);
    }

    private InvestmentRecommendationDTO getInvestmentRecommendationDTO(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = investmentRecommendationFetcher.investmentRecommendationData(stockCode);
        return investmentRecommendationParser.parseInvestmentRecommendation(response.getBody());
    }

    private String getStockCodeByName(String stockName) {
        return stockRepository.findByStockName(stockName)
                .map(Stock::getStockCode)
                .orElseThrow(() -> {
                    System.out.println("주식명: " + stockName + "을(를) 찾을 수 없습니다.");
                    return new RuntimeException("해당 주식명을 찾을 수 없습니다: " + stockName);
                });
    }

}
