package com.example.investment_api.virtual.calculator.controller;

import com.example.investment_api.virtual.account.dto.AllResultDTO;
import com.example.investment_api.virtual.account.dto.AccountStockData;
import com.example.investment_api.virtual.account.service.StockDataService;
import com.example.investment_api.virtual.calculator.mapper.StockCalculationMapper;
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

    private final StockDataService stockDataService;
    private final StockCalculationMapper stockCalculationMapper;

    public AllStockCalculatorController(StockDataService stockDataService, StockCalculationMapper stockCalculationMapper) {
        this.stockDataService = stockDataService;
        this.stockCalculationMapper = stockCalculationMapper;
    }

    @GetMapping()
    public ResponseEntity<AllResultDTO> getAllCalculations(@RequestParam Long memberId) {
        List<AccountStockData> dtoList = stockDataService.getAccountStockDataList(memberId);
        if (dtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AllResultDTO result = stockCalculationMapper.toAllResultDTO(dtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

