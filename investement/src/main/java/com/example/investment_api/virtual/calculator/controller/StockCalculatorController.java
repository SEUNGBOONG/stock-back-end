package com.example.investment_api.virtual.calculator.controller;


import com.example.investment_api.virtual.account.MemberAccountService;
import com.example.investment_api.virtual.calculator.domain.StockCalculator;
import com.example.investment_api.virtual.calculator.dto.StockCalculationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/calculator/stock")
public class StockCalculatorController {

    private final StockCalculator stockCalculator;
    private final MemberAccountService memberAccountService;

    public StockCalculatorController(StockCalculator stockCalculator, MemberAccountService memberAccountService) {
        this.stockCalculator = stockCalculator;
        this.memberAccountService = memberAccountService;
    }

    @GetMapping("/evaluation-profit")
    public ResponseEntity<Double> getEvaluationProfit(@RequestParam Long memberId, @RequestParam String stockName) {
        StockCalculationDTO dto = memberAccountService.getStockCalculationDTOList(memberId, stockName);
        double result = stockCalculator.calculateEvaluationProfit(dto.currentPrice(), dto.buyPrice(), dto.stockCount());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/profit-rate")
    public ResponseEntity<Double> getProfitRate(@RequestParam Long memberId, @RequestParam String stockName) {
        StockCalculationDTO dto = memberAccountService.getStockCalculationDTOList(memberId, stockName);
        double result = stockCalculator.calculateProfitRate(dto.currentPrice(), dto.buyPrice());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/purchase-amount")
    public ResponseEntity<Double> getPurchaseAmount(@RequestParam Long memberId, @RequestParam String stockName) {
        StockCalculationDTO dto = memberAccountService.getStockCalculationDTOList(memberId, stockName);
        double result = stockCalculator.calculatePurchaseAmount(dto.buyPrice(), dto.stockCount());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/evaluation-amount")
    public ResponseEntity<Integer> getEvaluationAmount(@RequestParam Long memberId, @RequestParam String stockName) {
        StockCalculationDTO dto = memberAccountService.getStockCalculationDTOList(memberId, stockName);
        int result = stockCalculator.calculateEvaluationAmount(dto.currentPrice(), dto.stockCount());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
