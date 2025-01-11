package com.example.investment_api.search.base.searchHome.service;

import com.example.investment_api.home.marketCapitalization.service.client.MarketCapitalizationFetcher;

import com.example.investment_api.search.base.searchHome.infrastructure.StockDataParser;
import com.example.investment_api.search.base.searchHome.controller.dto.StockDataDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StockDataService {

    private final MarketCapitalizationFetcher marketCapitalizationFetcher;

    private final StockDataParser stockDataParser;

    @Autowired
    public StockDataService(final MarketCapitalizationFetcher marketCapitalizationFetcher, final StockDataParser stockDataParser) {
        this.marketCapitalizationFetcher = marketCapitalizationFetcher;
        this.stockDataParser = stockDataParser;
    }

    public List<StockDataDTO> getStockDataDTO(int offset, int limit, int pageSize) throws IOException {
        ResponseEntity<String> response = getStringResponseEntity();
        List<StockDataDTO> allStockData = stockDataParser.parse(response.getBody());

        int end = Math.min(offset + pageSize, allStockData.size());
        if (offset >= allStockData.size() || offset < 0) {
            return List.of();
        }
        return allStockData.subList(offset, end);
    }

    private ResponseEntity<String> getStringResponseEntity() {
        return marketCapitalizationFetcher.marketCapitalizationData();
    }
}
