package com.example.investment.home.fluctuation.service;


import com.example.investment.home.fluctuation.controller.dto.response.FluctuationDTO;
import com.example.investment.home.fluctuation.infrastructor.FluctuationParser;
import com.example.investment.home.fluctuation.service.client.FluctuationDataFetcher;

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
        ResponseEntity<String> response = fluctuationDataFetcher.fluctuationData();
        return fluctuationParser.getFluctuation(response.getBody());
    }
}
