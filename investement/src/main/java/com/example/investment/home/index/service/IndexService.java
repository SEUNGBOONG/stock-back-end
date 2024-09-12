package com.example.investment.home.index.service;

import com.example.investment.home.index.controller.dto.KOSDAQResponse;
import com.example.investment.home.index.controller.dto.KOSPIResponse;
import com.example.investment.home.index.service.client.IndexFetcher;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class IndexService {

    private final IndexFetcher indexFetcher;

    public IndexService(IndexFetcher indexFetcher) {
        this.indexFetcher = indexFetcher;
    }

    public KOSPIResponse getKOSPIIndex() {
        JSONObject jsonObject = indexFetcher.fetchKOSPIData();
        return parseKOSPIResponse(jsonObject);
    }

    public KOSDAQResponse getKOSDAQIndex() {
        JSONObject jsonObject = indexFetcher.fetchKOSDAQData();
        return parseKOSDAQResponse(jsonObject);
    }

    private KOSPIResponse parseKOSPIResponse(JSONObject jsonObject) {
        JSONArray items = jsonObject
                .getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");
        JSONObject indexData = items.getJSONObject(0);

        String indexName = indexData.getString("idxNm");
        String indexValue = indexData.getString("clpr");
        String fluctuationRate = indexData.getString("fltRt");

        return new KOSPIResponse(indexName, indexValue, fluctuationRate);
    }

    private KOSDAQResponse parseKOSDAQResponse(JSONObject jsonObject) {
        JSONArray items = jsonObject
                .getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");

        JSONObject indexData = items.getJSONObject(0);
        String indexName = indexData.getString("idxNm");
        String indexValue = indexData.getString("clpr");
        String fluctuationRate = indexData.getString("fltRt");

        return new KOSDAQResponse(indexName, indexValue, fluctuationRate);
    }
}
