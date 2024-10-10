package com.example.investment_api.home.marketCapitalization.service;

import jakarta.transaction.Transactional;

import com.example.investment_api.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;

import com.example.investment_api.home.marketCapitalization.infrastructure.MarketCapitalizationParser;

import com.example.investment_api.home.marketCapitalization.service.client.MarketCapitalizationFetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class MarketCapitalizationService {

    private final MarketCapitalizationFetcher marketCapitalizationFetcher;

    private final MarketCapitalizationParser marketCapitalizationParser;

    @Autowired
    public MarketCapitalizationService(final MarketCapitalizationFetcher marketCapitalizationFetcher, final MarketCapitalizationParser marketCapitalizationParser) {
        this.marketCapitalizationFetcher = marketCapitalizationFetcher;
        this.marketCapitalizationParser = marketCapitalizationParser;
    }

    public List<MarketCapitalizationDTO> getMarketCapitalization() throws IOException {
        return getMarketCapitalizationDTOS();
    }

    private List<MarketCapitalizationDTO> getMarketCapitalizationDTOS() throws IOException {
        ResponseEntity<String> response = getStringResponseEntity();
        return marketCapitalizationParser.parse(response.getBody());
    }

    private ResponseEntity<String> getStringResponseEntity() {
        return marketCapitalizationFetcher.marketCapitalizationData();
    }
}
