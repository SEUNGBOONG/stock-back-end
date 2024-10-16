package com.example.investment_api.search.financialRatio.controller;

import com.example.investment_api.search.financialRatio.controller.dto.FinancialRatioDTO;

import com.example.investment_api.search.financialRatio.service.FinancialRatioService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search/financialRatio")
public class FinancialRatioController {

    private final FinancialRatioService financialRatioService;

    public FinancialRatioController(FinancialRatioService financialRatioService) {
        this.financialRatioService = financialRatioService;
    }

    @GetMapping
    @CrossOrigin(origins = {"http://13.209.73.178:80", "http://localhost:3000"})
    public ResponseEntity<List<FinancialRatioDTO>> getFinancialRatio(@RequestParam String stockInfo) throws IOException {
        return getListResponseEntity(stockInfo);
    }

    private ResponseEntity<List<FinancialRatioDTO>> getListResponseEntity(final String stockInfo) throws IOException {
        List<FinancialRatioDTO> financialRatioDTOS = financialRatioService.getFinancialRatio(stockInfo);
        return ResponseEntity.ok(financialRatioDTOS);
    }
}

