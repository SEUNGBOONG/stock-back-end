package com.example.investment.financialRatio.controller;

import com.example.investment.financialRatio.controller.dto.FinancialRatioDTO;
import com.example.investment.financialRatio.service.FinancialRatioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/financial")
public class FinancialRatioController {

    private final FinancialRatioService financialRatioService;

    public FinancialRatioController(FinancialRatioService financialRatioService) {
        this.financialRatioService = financialRatioService;
    }

    @GetMapping("/ratio")
    public List<FinancialRatioDTO> getFinancialRatio(@RequestParam String fidInputIscd) {
        return financialRatioService.getFinancialRatio(fidInputIscd);
    }
}
