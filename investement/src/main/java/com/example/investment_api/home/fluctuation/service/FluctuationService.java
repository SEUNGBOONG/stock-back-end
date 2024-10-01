package com.example.investment_api.home.fluctuation.service;


import com.example.investment_api.home.fluctuation.controller.dto.response.FluctuationDTO;

import com.example.investment_api.home.fluctuation.infrastructure.FluctuationParser;

import com.example.investment_api.home.fluctuation.service.client.FluctuationDataFetcher;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class FluctuationService {

    private final FluctuationDataFetcher fluctuationDataFetcher;

    private final FluctuationParser fluctuationParser;

    @Autowired
    public FluctuationService(FluctuationDataFetcher fluctuationDataFetcher, FluctuationParser fluctuationParser) {
        this.fluctuationDataFetcher = fluctuationDataFetcher;
        this.fluctuationParser=fluctuationParser;
    }

    public List<FluctuationDTO> getFluctuation() throws IOException {
        return getFluctuationDTOS();
    }

    private List<FluctuationDTO> getFluctuationDTOS() throws IOException {
        ResponseEntity<String> response = fluctuationDataFetcher.fluctuationData();
        return fluctuationParser.getFluctuation(response.getBody());
    }
}
