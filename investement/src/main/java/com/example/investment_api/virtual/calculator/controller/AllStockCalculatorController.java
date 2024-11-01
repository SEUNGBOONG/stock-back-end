package com.example.investment_api.virtual.calculator.controller;

import com.example.investment_api.virtual.account.dto.AllResultDTO;
import com.example.investment_api.virtual.account.service.MemberAccountService;
import com.example.investment_api.virtual.calculator.domain.AllStockCalculator;
import com.example.investment_api.virtual.account.dto.StockCalculationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("account/all-stocks")
public class AllStockCalculatorController {

    private final MemberAccountService memberAccountService;
    private final AllStockCalculator allStockCalculator;

    public AllStockCalculatorController(MemberAccountService memberAccountService, AllStockCalculator allStockCalculator) {
        this.memberAccountService = memberAccountService;
        this.allStockCalculator = allStockCalculator;
    }

    @GetMapping()
    public ResponseEntity<AllResultDTO> getAllCalculations(@RequestParam Long memberId) {
        List<StockCalculationDTO> dtoList = memberAccountService.getStockCalculationDtoList(memberId);

        double totalEvaluationProfit = allStockCalculator.calculateTotalEvaluationProfit(dtoList);
        double totalPurchaseAmount = allStockCalculator.calculateTotalPurchaseAmount(dtoList);
        double totalProfit = allStockCalculator.calculateTotalProfit(dtoList);
        int totalEvaluationAmount = allStockCalculator.calculateTotalEvaluationAmount(dtoList);

        AllResultDTO result = new AllResultDTO(totalEvaluationProfit, totalPurchaseAmount, totalProfit, totalEvaluationAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

