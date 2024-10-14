package com.example.investment_api.search.stock.controller;

import com.example.investment_api.search.stock.controller.dto.StockResponse;

import com.example.investment_api.search.stock.service.StockService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
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
    @CrossOrigin(origins = "http://13.209.4.56:8080")
    public ResponseEntity<StockResponse> getFinancialRatio(@RequestParam String stockInfo) throws IOException {
        StockResponse stockResponse = stockService.getStockResponse(stockInfo);
        return ResponseEntity.ok(stockResponse);
    }
}
