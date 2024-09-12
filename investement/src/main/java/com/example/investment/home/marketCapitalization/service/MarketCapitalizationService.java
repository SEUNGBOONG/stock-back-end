package com.example.investment.home.marketCapitalization.service;

import com.example.investment.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;
import com.example.investment.home.marketCapitalization.infrastructure.MarketCapitalizationParser;
import com.example.investment.home.marketCapitalization.service.client.MarketCapitalizationFetcher;
import com.example.investment.home.tradingVolume.controller.dto.TradingVolumeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MarketCapitalizationService {

    private final MarketCapitalizationFetcher marketCapitalizationFetcher;
    private final MarketCapitalizationParser marketCapitalizationParser;

    public MarketCapitalizationService(final MarketCapitalizationFetcher marketCapitalizationFetcher, final MarketCapitalizationParser marketCapitalizationParser) {
        this.marketCapitalizationFetcher = marketCapitalizationFetcher;
        this.marketCapitalizationParser = marketCapitalizationParser;
    }

    public List<MarketCapitalizationDTO> getTradingVolume() throws IOException {
        ResponseEntity<String> response = marketCapitalizationFetcher.marketCapitalizationData();
        return marketCapitalizationParser.getTradingVolume(response.getBody());
    }
}
