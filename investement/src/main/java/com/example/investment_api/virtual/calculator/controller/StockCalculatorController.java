package com.example.investment_api.virtual.calculator.controller;

import com.example.investment_api.virtual.account.controller.dto.ResultDTO;
import com.example.investment_api.virtual.account.controller.dto.AccountStockData;
import com.example.investment_api.virtual.account.service.StockDataTransferService;
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
    private final StockDataTransferService stockDataTransferService;

    public StockCalculatorController(StockCalculationMapper stockCalculationService, StockDataTransferService stockDataTransferService) {
        this.stockCalculationMapper = stockCalculationService;
        this.stockDataTransferService = stockDataTransferService;
    }

    @GetMapping()
    public ResponseEntity<ResultDTO> getAllCalculations(@RequestParam Long memberId, @RequestParam String stockName) {
        AccountStockData dto = stockDataTransferService.getAccountStockData(memberId, stockName);
        ResultDTO result = stockCalculationMapper.toResultDTO(stockName, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
