package com.example.investment.home.fluctuation.service;


import com.example.investment.home.fluctuation.controller.dto.response.fluctuationDTO;
import com.example.investment.home.fluctuation.controller.dto.response.fluctuationResponse;
import com.example.investment.home.fluctuation.service.client.FluctuationDataFetcher;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FluctuationService {
    private final FluctuationDataFetcher fluctuationDataFetcher;

    public FluctuationService (FluctuationDataFetcher fluctuationDataFetcher) {
        this.fluctuationDataFetcher=fluctuationDataFetcher;
    }

    public List<fluctuationDTO> getTopFluctuation (String fidInputIscd) {
        fluctuationResponse response = fluctuationDataFetcher. fluctuationData(fidInputIscd);
        List<fluctuationDTO> allFluctuation= response.getOutput();

        return allFluctuation;
    }
}
