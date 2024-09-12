package com.example.investment.tradingVolume.controller;

import com.example.investment.tradingVolume.controller.dto.TradingVolumeDTO;
import com.example.investment.tradingVolume.service.TradingVolumeService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tradingVolume")
public class TradingVolumeController {

    private final TradingVolumeService tradingVolumeService;

    public TradingVolumeController(TradingVolumeService tradingVolumeService) {
        this.tradingVolumeService = tradingVolumeService;
    }

    @GetMapping
    public ResponseEntity<List<TradingVolumeDTO>> getTradingVolume() throws IOException {
        List<TradingVolumeDTO> tradingVolumes = tradingVolumeService.getTradingVolume();
        return ResponseEntity.ok(tradingVolumes);
    }
}
