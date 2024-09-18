package com.example.investment.home.tradingVolume.service;

import com.example.investment.home.tradingVolume.controller.dto.TradingVolumeDTO;

import com.example.investment.home.tradingVolume.infrastructure.TradingVolumeParser;

import com.example.investment.home.tradingVolume.service.client.TradingVolumeFetcher;

import org.springframework.beans.factory.annotation.Autowired;

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

    public List<TradingVolumeDTO> getTradingVolume() throws IOException {
        return getTradingVolumeDTOS();
    }

    private List<TradingVolumeDTO> getTradingVolumeDTOS() throws IOException {
        ResponseEntity<String> response = tradingVolumeFetcher.fetchTradingVolumeData();
        return tradingVolumeParser.getTradingVolume(response.getBody());
    }
}
