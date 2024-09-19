package com.example.investment_api.search.financialRatio.service;

import com.example.investment_api.search.financialRatio.controller.dto.FinancialRatioDTO;

import com.example.investment_api.search.financialRatio.infrastructure.FinancialRatioParser;

import com.example.investment_api.search.financialRatio.service.client.FinancialRatioDataFetcher;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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