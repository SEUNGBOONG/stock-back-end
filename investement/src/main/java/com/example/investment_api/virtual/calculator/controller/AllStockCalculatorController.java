package com.example.investment_api.virtual.calculator.controller;

import com.example.investment_api.global.annotation.Member;
import com.example.investment_api.virtual.account.controller.dto.AllResultDTO;
import com.example.investment_api.virtual.account.controller.dto.AccountStockData;
import com.example.investment_api.virtual.account.service.StockDataTransferService;
import com.example.investment_api.virtual.calculator.mapper.StockCalculationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("account/all-stocks")
public class AllStockCalculatorController {

    private final StockDataTransferService stockDataTransferService;
    private final StockCalculationMapper stockCalculationMapper;

    public AllStockCalculatorController(StockDataTransferService stockDataTransferService, StockCalculationMapper stockCalculationMapper) {
        this.stockDataTransferService = stockDataTransferService;
        this.stockCalculationMapper = stockCalculationMapper;
    }

    @GetMapping()
    public ResponseEntity<AllResultDTO> getAllCalculations(@Member Long memberId) {
        List<AccountStockData> dtoList = stockDataTransferService.getAccountStockDataList(memberId);
        if (dtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AllResultDTO result = stockCalculationMapper.toAllResultDTO(dtoList, memberId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
