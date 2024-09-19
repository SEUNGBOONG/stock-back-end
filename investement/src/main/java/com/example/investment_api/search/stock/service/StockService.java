package com.example.investment_api.search.stock.service;

import com.example.investment_api.search.stock.controller.dto.StockResponse;

import com.example.investment_api.search.stock.infrastructure.StockParser;

import com.example.investment_api.search.stock.service.client.StockDataFetcher;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Transactional
public class StockService {

    private final StockDataFetcher stockDataFetcher;
    private final StockParser stockParser;

    @Autowired
    public StockService(final StockDataFetcher stockDataFetcher, final StockParser stockParser) {
        this.stockDataFetcher = stockDataFetcher;
        this.stockParser = stockParser;
    }

    public StockResponse getStockResponse(final String stockInfo) throws IOException {
        ResponseEntity<String> response = stockDataFetcher.fetchStockData(stockInfo);
        return stockParser.parse(response.getBody());
    }
}
