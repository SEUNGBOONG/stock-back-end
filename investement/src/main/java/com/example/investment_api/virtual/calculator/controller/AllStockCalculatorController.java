package com.example.investment_api.virtual.calculator.controller;

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
@RequestMapping("account/all")
public class AllStockCalculatorController {

    private final MemberAccountService memberAccountService;

    public AllStockCalculatorController(MemberAccountService memberAccountService) {
        this.memberAccountService = memberAccountService;
    }

    @GetMapping("/evaluation-profit")
    public ResponseEntity<Double> getTotalEvaluationProfit(@RequestParam Long memberId) {
        List<StockCalculationDTO> dtoList = memberAccountService.getStockCalculationDtoList(memberId);
        AllStockCalculator calculator = new AllStockCalculator();
        double result = calculator.calculateTotalEvaluationProfit(dtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/evaluation-amount")
    public ResponseEntity<Integer> getTotalEvaluationAmount(@RequestParam Long memberId) {
        List<StockCalculationDTO> dtoList = memberAccountService.getStockCalculationDtoList(memberId);
        AllStockCalculator calculator = new AllStockCalculator();
        int result = calculator.calculateTotalEvaluationAmount(dtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/profit")
    public ResponseEntity<Double> getTotalProfit(@RequestParam Long memberId) {
        List<StockCalculationDTO> dtoList = memberAccountService.getStockCalculationDtoList(memberId);
        AllStockCalculator calculator = new AllStockCalculator();
        double result = calculator.calculateTotalProfit(dtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/purchase-amount")
    public ResponseEntity<Double> getTotalPurchaseAmount(@RequestParam Long memberId) {
        List<StockCalculationDTO> dtoList = memberAccountService.getStockCalculationDtoList(memberId);
        AllStockCalculator calculator = new AllStockCalculator();
        double result = calculator.calculateTotalPurchaseAmount(dtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

