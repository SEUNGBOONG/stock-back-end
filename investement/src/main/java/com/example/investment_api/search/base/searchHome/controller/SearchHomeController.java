package com.example.investment_api.search.base.searchHome.controller;

import com.example.investment_api.search.base.searchHome.dto.StockDataDTO;

import com.example.investment_api.search.base.searchHome.service.StockDataService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchHomeController {

    private final StockDataService stockDataService;

    public SearchHomeController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @GetMapping("/stock-data")
    public ResponseEntity<List<StockDataDTO>> getStockData(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            List<StockDataDTO> stockDataList = stockDataService.getStockDataDTO(offset, limit, pageSize);
            return ResponseEntity.ok(stockDataList);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
