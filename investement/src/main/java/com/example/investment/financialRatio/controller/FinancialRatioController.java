package com.example.investment.financialRatio.controller;

import com.example.investment.financialRatio.controller.dto.FinancialRatioDTO;
import com.example.investment.financialRatio.service.FinancialRatioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/financialRatio")
public class FinancialRatioController {

    private final FinancialRatioService financialRatioService;

    public FinancialRatioController(FinancialRatioService financialRatioService) {
        this.financialRatioService = financialRatioService;
    }

    @GetMapping
    public ResponseEntity<List<FinancialRatioDTO>> getFinancialRatio(@RequestParam String fid_input_iscd) throws IOException {
        List<FinancialRatioDTO> financialRatioDTOS = financialRatioService.getFinancialRatio(fid_input_iscd);
        return ResponseEntity.ok(financialRatioDTOS);
    }
}

