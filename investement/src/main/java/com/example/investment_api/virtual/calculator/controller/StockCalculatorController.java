package com.example.investment_api.virtual.calculator.controller;

import com.example.investment_api.virtual.account.dto.resultDTO;
import com.example.investment_api.virtual.account.dto.AccountStockData;
import com.example.investment_api.virtual.account.service.StockDataService;
import com.example.investment_api.virtual.calculator.mapper.StockCalculationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account/stocks")
public class StockCalculatorController {

    private final StockCalculationMapper stockCalculationMapper;
    private final StockDataService stockDataService;

    public StockCalculatorController(StockCalculationMapper stockCalculationService, StockDataService stockDataService) {
        this.stockCalculationMapper = stockCalculationService;
        this.stockDataService = stockDataService;
    }

    @GetMapping()
    public ResponseEntity<resultDTO> getAllCalculations(@RequestParam Long memberId, @RequestParam String stockName) {
        AccountStockData dto = stockDataService.getAccountStockData(memberId, stockName);
        resultDTO result = stockCalculationMapper.toResultDTO(stockName, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
