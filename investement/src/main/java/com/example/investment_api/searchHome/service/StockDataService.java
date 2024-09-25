package com.example.investment_api.searchHome.service;

import com.example.investment_api.home.marketCapitalization.service.client.MarketCapitalizationFetcher;

import com.example.investment_api.searchHome.dto.StockDataDTO;
import com.example.investment_api.searchHome.infrastructure.StockDataParser;
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

    public List<StockDataDTO> getStockData() throws IOException {
        return getStockDataDTOS();
    }

    private List<StockDataDTO> getStockDataDTOS() throws IOException {
        ResponseEntity<String> response = getStringResponseEntity();
        return stockDataParser.parse(response.getBody());
    }

    private ResponseEntity<String> getStringResponseEntity() {
        return marketCapitalizationFetcher.marketCapitalizationData();
    }
}
