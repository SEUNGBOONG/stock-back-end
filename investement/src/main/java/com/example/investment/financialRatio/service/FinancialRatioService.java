package com.example.investment.financialRatio.service;

import com.example.investment.financialRatio.controller.dto.FinancialRatioDTO;
import com.example.investment.financialRatio.controller.dto.FinancialRatioResponse;
import com.example.investment.financialRatio.service.client.FinancialRatioDataFetcher;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FinancialRatioService {

    private final FinancialRatioDataFetcher financialRatioDataFetcher;

    public FinancialRatioService(FinancialRatioDataFetcher financialRatioDataFetcher) {
        this.financialRatioDataFetcher = financialRatioDataFetcher;
    }

    public List<FinancialRatioDTO> getFinancialRatio(String fidInputIscd) {
        FinancialRatioResponse response = financialRatioDataFetcher.fetchFinancialRatioData(fidInputIscd);
        return response.getOutput();
    }
}
