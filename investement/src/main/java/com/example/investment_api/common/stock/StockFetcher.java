package com.example.investment_api.common.stock;

import org.springframework.http.ResponseEntity;

public interface StockFetcher {
    ResponseEntity<String> fetchStockData(String stockInfo);
}
