package com.example.investment.home.index.infrastructure;

import com.example.investment.home.index.controller.dto.KOSDAQResponse;
import com.example.investment.home.index.controller.dto.KOSPIResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class IndexParser {

    public KOSPIResponse parseKOSPIResponse(JSONObject jsonObject) {
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

    public KOSDAQResponse parseKOSDAQResponse(JSONObject jsonObject) {
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
