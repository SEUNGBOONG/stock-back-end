package com.example.investment_api.virtual.calculator.controller;


import com.example.investment_api.virtual.account.dto.resultDTO;
import com.example.investment_api.virtual.account.service.MemberAccountService;
import com.example.investment_api.virtual.calculator.domain.StockCalculator;
import com.example.investment_api.virtual.account.dto.StockCalculationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account/stocks")
public class StockCalculatorController {

    private final StockCalculator stockCalculator;
    private final MemberAccountService memberAccountService;

    public StockCalculatorController(StockCalculator stockCalculator, MemberAccountService memberAccountService) {
        this.stockCalculator = stockCalculator;
        this.memberAccountService = memberAccountService;
    }

    @GetMapping()
    public ResponseEntity<resultDTO> getAllCalculations(@RequestParam Long memberId, @RequestParam String stockName) {
        StockCalculationDTO dto = memberAccountService.getStockCalculationDTOList(memberId, stockName);

        String name = stockName;
        int currentPrice = dto.currentPrice();
        int stockCount= dto.stockCount();
        double evaluationProfit = stockCalculator.calculateEvaluationProfit(dto.buyPrice(), dto.currentPrice(), dto.stockCount());
        double profitRate = stockCalculator.calculateProfitRate(dto.buyPrice(), dto.currentPrice());
        int purchaseAmount = stockCalculator.calculatePurchaseAmount(dto.buyPrice(), dto.stockCount());
        int evaluationAmount = stockCalculator.calculateEvaluationAmount(dto.currentPrice(), dto.stockCount());

        resultDTO result = new resultDTO(name, currentPrice, stockCount, evaluationProfit, profitRate, purchaseAmount, evaluationAmount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
