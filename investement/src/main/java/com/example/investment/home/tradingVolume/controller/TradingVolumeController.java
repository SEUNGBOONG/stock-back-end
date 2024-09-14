package com.example.investment.home.tradingVolume.controller;

import com.example.investment.home.tradingVolume.controller.dto.TradingVolumeDTO;
import com.example.investment.home.tradingVolume.service.TradingVolumeService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/home/tradingVolume")
public class TradingVolumeController {

    private final TradingVolumeService tradingVolumeService;

    public TradingVolumeController(TradingVolumeService tradingVolumeService) {
        this.tradingVolumeService = tradingVolumeService;
    }

    @GetMapping
    public ResponseEntity<List<TradingVolumeDTO>> getTradingVolume() throws IOException {
        return getListResponseEntity();
    }

    private ResponseEntity<List<TradingVolumeDTO>> getListResponseEntity() throws IOException {
        List<TradingVolumeDTO> tradingVolumes = tradingVolumeService.getTradingVolume();
        return ResponseEntity.ok(tradingVolumes);
    }
}
