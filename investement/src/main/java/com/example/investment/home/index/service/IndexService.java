package com.example.investment.home.index.service;

import com.example.investment.home.index.controller.dto.KOSDAQResponse;
import com.example.investment.home.index.controller.dto.KOSPIResponse;

import com.example.investment.home.index.infrastructure.IndexParser;

import com.example.investment.home.index.service.client.IndexFetcher;

import jakarta.transaction.Transactional;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class IndexService {

    private final IndexFetcher indexFetcher;
    private final IndexParser indexParser;

    @Autowired
    public IndexService(final IndexFetcher indexFetcher, final IndexParser indexParser) {
        this.indexFetcher = indexFetcher;
        this.indexParser = indexParser;
    }

    public KOSPIResponse getKOSPIIndex() {
        return getKospiResponse();
    }

    public KOSDAQResponse getKOSDAQIndex() {
        return getKosdaqResponse();
    }

    private KOSPIResponse getKospiResponse() {
        ResponseEntity<String> response = indexFetcher.getKOSPIIndexData();
        JSONObject jsonResponse = new JSONObject(response.getBody());
        return indexParser.parseKOSPIResponse(jsonResponse);
    }

    private KOSDAQResponse getKosdaqResponse() {
        ResponseEntity<String> response = indexFetcher.getKOSDAQIndexData();
        JSONObject jsonResponse = new JSONObject(response.getBody());
        return indexParser.parseKOSDAQResponse(jsonResponse);
    }
}
