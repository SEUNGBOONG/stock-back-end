package com.example.investment.home.marketCapitalization.controller;

import com.example.investment.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;

import com.example.investment.home.marketCapitalization.service.MarketCapitalizationService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/home/marketCapitalization")
public class MarketCapitalizationController {

    private final MarketCapitalizationService marketCapitalizationService;

    public MarketCapitalizationController(final MarketCapitalizationService marketCapitalizationService) {
        this.marketCapitalizationService = marketCapitalizationService;
    }

    @GetMapping
    public ResponseEntity<List<MarketCapitalizationDTO>> getTradingVolume() throws IOException {
        return getListResponseEntity();
    }

    private ResponseEntity<List<MarketCapitalizationDTO>> getListResponseEntity() throws IOException {
        List<MarketCapitalizationDTO> marketCapitalizationDTOList = marketCapitalizationService.getMarketCapitalization();
        return ResponseEntity.ok(marketCapitalizationDTOList);
    }
}
