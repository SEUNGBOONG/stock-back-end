package com.example.investment.home.marketCapitalization.service;

import com.example.investment.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;
import com.example.investment.home.marketCapitalization.infrastructure.MarketCapitalizationParser;
import com.example.investment.home.marketCapitalization.service.client.MarketCapitalizationFetcher;
import jakarta.transaction.Transactional;
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
        ResponseEntity<String> response = marketCapitalizationFetcher.marketCapitalizationData();
        return marketCapitalizationParser.parse(response.getBody());
    }
}
