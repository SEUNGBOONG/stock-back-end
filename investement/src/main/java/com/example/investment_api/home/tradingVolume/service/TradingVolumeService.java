package com.example.investment_api.home.tradingVolume.service;

import com.example.investment_api.home.tradingVolume.controller.dto.TradingVolumeDTO;

import com.example.investment_api.home.tradingVolume.infrastructure.TradingVolumeParser;

import com.example.investment_api.home.tradingVolume.service.client.TradingVolumeFetcher;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class TradingVolumeService {

    private final TradingVolumeFetcher tradingVolumeFetcher;

    private final TradingVolumeParser tradingVolumeParser;

    @Autowired
    public TradingVolumeService(TradingVolumeFetcher tradingVolumeFetcher, TradingVolumeParser tradingVolumeParser) {
        this.tradingVolumeFetcher = tradingVolumeFetcher;
        this.tradingVolumeParser = tradingVolumeParser;
    }

    @Cacheable(value = "tradingVolumeCache", key = "'tradingVolume'", unless = "#result == null or #result.isEmpty()")
    public List<TradingVolumeDTO> getTradingVolume() throws IOException {
        return getTradingVolumeDTOS();
    }

    private List<TradingVolumeDTO> getTradingVolumeDTOS() throws IOException {
        ResponseEntity<String> response = tradingVolumeFetcher.fetchTradingVolumeData();
        return tradingVolumeParser.getTradingVolume(response.getBody());
    }
}
