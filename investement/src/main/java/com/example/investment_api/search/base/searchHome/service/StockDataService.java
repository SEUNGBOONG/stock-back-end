package com.example.investment_api.search.base.searchHome.service;

import com.example.investment_api.home.marketCapitalization.service.client.MarketCapitalizationFetcher;

import com.example.investment_api.search.base.searchHome.infrastructure.StockDataParser;
import com.example.investment_api.search.base.searchHome.dto.StockDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public List<StockDataDTO> getStockDataDTO(int page, int size) throws IOException {
        ResponseEntity<String> response = getStringResponseEntity();
        List<StockDataDTO> allStockData = stockDataParser.parse(response.getBody());

        int start = (page - 1) * size;
        int end = Math.min(start + size, allStockData.size());

        return allStockData.subList(start, end);
    }

    private ResponseEntity<String> getStringResponseEntity() {
        return marketCapitalizationFetcher.marketCapitalizationData();
    }
}
