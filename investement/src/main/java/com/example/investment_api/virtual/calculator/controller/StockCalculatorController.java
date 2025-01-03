package com.example.investment_api.virtual.calculator.controller;

import com.example.investment_api.global.annotation.Member;
import com.example.investment_api.virtual.account.controller.dto.ResultDTO;
import com.example.investment_api.virtual.account.controller.dto.AccountStockData;
import com.example.investment_api.virtual.account.controller.dto.UserStockDTO;
import com.example.investment_api.virtual.account.service.OrderService;
import com.example.investment_api.virtual.account.service.StockDataTransferService;
import com.example.investment_api.virtual.calculator.mapper.StockCalculationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<ResultDTO>> getAllCalculations(@Member Long memberId) {
        UserStockDTO userStockDTO = stockDataTransferService.getUserStockNames(memberId);
        List<String> stockNames = userStockDTO.stockNames();

        List<ResultDTO> results = stockNames.stream()
                .map(stockName -> {
                    AccountStockData dto = stockDataTransferService.getAccountStockData(memberId, stockName);
                    return stockCalculationMapper.toResultDTO(stockName, dto);
                })
                .toList();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
