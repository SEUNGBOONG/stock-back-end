package com.example.investment_api.search.detail.stock.controller;

import com.example.investment_api.search.detail.stock.controller.dto.StockResponse;

import com.example.investment_api.search.detail.stock.service.StockService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/search/stock")
public class StockController {

    private final StockService stockService;

    public StockController(final StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<StockResponse> getFinancialRatio(@RequestParam String stockName) throws IOException {
        StockResponse stockResponse = stockService.getStockResponse(stockName);
        return ResponseEntity.ok(stockResponse);
    }
}
