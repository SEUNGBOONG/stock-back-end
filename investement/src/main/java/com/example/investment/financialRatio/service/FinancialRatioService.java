package com.example.investment.financialRatio.service;

import com.example.investment.financialRatio.controller.dto.FinancialRatioDTO;

import com.example.investment.financialRatio.infrastructure.FinancialRatioParser;

import com.example.investment.financialRatio.service.client.FinancialRatioDataFetcher;

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

    public List<FinancialRatioDTO> getFinancialRatio(String fidInputIscd) throws IOException {
        ResponseEntity<String> response = financialRatioDataFetcher.fetchFinancialRatioData(fidInputIscd);
        return financialRatioParser.parseFinancialRatio(response.getBody());
    }
}
