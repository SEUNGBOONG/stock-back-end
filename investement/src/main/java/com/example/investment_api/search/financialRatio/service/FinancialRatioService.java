package com.example.investment_api.search.financialRatio.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;

import com.example.investment_api.search.financialRatio.controller.dto.FinancialRatioDTO;
import com.example.investment_api.search.financialRatio.service.client.FinancialRatioDataFetcher;
import com.example.investment_api.search.financialRatio.infrastructure.FinancialRatioParser;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class FinancialRatioService {

    private final FinancialRatioDataFetcher financialRatioDataFetcher;
    private final FinancialRatioParser financialRatioParser;
    private final StockRepository stockRepository;

    @Autowired
    public FinancialRatioService(FinancialRatioDataFetcher financialRatioDataFetcher, FinancialRatioParser financialRatioParser, StockRepository stockRepository) {
        this.financialRatioDataFetcher = financialRatioDataFetcher;
        this.financialRatioParser = financialRatioParser;
        this.stockRepository = stockRepository;
    }

    public List<FinancialRatioDTO> getFinancialRatio(String stockName) throws IOException {
        return getFinancialRatioDTOS(stockName);
    }

    private List<FinancialRatioDTO> getFinancialRatioDTOS(final String stockName) throws IOException {
        Stock stock = stockRepository.findByStockName(stockName)
                .orElseThrow(() -> new RuntimeException("주식명: " + stockName + "을(를) 찾을 수 없습니다."));
        ResponseEntity<String> response = financialRatioDataFetcher.fetchFinancialRatioData(stock.getStockCode());
        return financialRatioParser.parseFinancialRatio(response.getBody());
    }
}
