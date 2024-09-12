package com.example.investment.index.service.client;

import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;

@Component
public class IndexFetcher {

    private final IndexApiService indexApiService;

    public IndexFetcher(IndexApiService indexApiService) {
        this.indexApiService = indexApiService;
    }

    public JSONObject fetchKOSPIData() {
        ResponseEntity<String> response = indexApiService.getKOSPIIndexData();
        return new JSONObject(response.getBody());
    }

    public JSONObject fetchKOSDAQData() {
        ResponseEntity<String> response = indexApiService.getKOSDAQIndexData();
        return new JSONObject(response.getBody());
    }
}
