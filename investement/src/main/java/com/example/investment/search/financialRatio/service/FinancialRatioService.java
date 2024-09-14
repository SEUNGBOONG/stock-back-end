package com.example.investment.search.financialRatio.service;

import com.example.investment.search.financialRatio.controller.dto.FinancialRatioDTO;

import com.example.investment.search.financialRatio.infrastructure.FinancialRatioParser;

import com.example.investment.search.financialRatio.service.client.FinancialRatioDataFetcher;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class FinancialRatioService {

    private final FinancialRatioDataFetcher financialRatioDataFetcher;
    private final FinancialRatioParser financialRatioParser;

    public FinancialRatioService(FinancialRatioDataFetcher financialRatioDataFetcher, FinancialRatioParser financialRatioParser) {
        this.financialRatioDataFetcher = financialRatioDataFetcher;
        this.financialRatioParser = financialRatioParser;
    }

    public List<FinancialRatioDTO> getFinancialRatio(String stockInfo) throws IOException {
        return getFinancialRatioDTOS(stockInfo);
    }

    private List<FinancialRatioDTO> getFinancialRatioDTOS(final String stockInfo) throws IOException {
        ResponseEntity<String> response = financialRatioDataFetcher.fetchFinancialRatioData(stockInfo);
        return financialRatioParser.parseFinancialRatio(response.getBody());
    }
}
