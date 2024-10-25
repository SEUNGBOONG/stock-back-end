package com.example.investment_api.search.detail.stock.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;

import com.example.investment_api.search.detail.stock.infrastructure.StockParser;
import com.example.investment_api.search.detail.stock.controller.dto.StockResponse;

import com.example.investment_api.search.detail.stock.service.client.StockDataFetcher;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Service
@Transactional
public class StockService {

    private final StockRepository stockRepository;

    private final StockDataFetcher stockDataFetcher;

    private final StockParser stockParser;

    @Autowired
    public StockService(final StockRepository stockRepository, final StockDataFetcher stockDataFetcher, final StockParser stockParser) {
        this.stockRepository = stockRepository;
        this.stockDataFetcher = stockDataFetcher;
        this.stockParser = stockParser;
    }

    public StockResponse getStockResponse(final String stockName) throws IOException {
        return getResponse(stockName);
    }

    private StockResponse getResponse(final String stockName) throws IOException {
        Stock stock = stockRepository.findByStockName(stockName)
                .orElseThrow(() -> new RuntimeException("주식명: " + stockName + "을(를) 찾을 수 없습니다."));
        ResponseEntity<String> response = stockDataFetcher.fetchStockData(stock.getStockCode());
        return stockParser.parse(response.getBody());
    }
}
