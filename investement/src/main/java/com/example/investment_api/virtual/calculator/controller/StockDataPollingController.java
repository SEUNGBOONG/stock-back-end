package com.example.investment_api.virtual.calculator.controller;


import com.example.investment_api.virtual.account.controller.dto.StockData;
import com.example.investment_api.virtual.calculator.infrastructure.scheduler.AccountDataPollingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// 확인용 테스트 컨트롤러
@RestController
public class StockDataPollingController {

    private final AccountDataPollingService stockDataPollingService;

    public StockDataPollingController(AccountDataPollingService stockDataPollingService) {
        this.stockDataPollingService = stockDataPollingService;
    }

    @GetMapping("/api/polling/stocks")
    public StockData getLatestStockData(@RequestParam String stockName) {
        return stockDataPollingService.getLatestStockData(stockName);
    }
}
