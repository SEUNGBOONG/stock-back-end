package com.example.investment_api.home.index.infrastructure;

import com.example.investment_api.home.index.controller.dto.KOSDAQResponse;

import com.example.investment_api.home.index.controller.dto.KOSPIResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.stereotype.Component;

@Component
public class IndexParser {

    public static final String INDEX_NAME = "idxNm";
    public static final String INDEX_VALUE = "clpr";
    public static final String FLUCTUATION_RATE = "fltRt";
    public static final String RESPONSE = "response";
    public static final String BODY = "body";
    public static final String ITEMS = "items";
    public static final String ITEM = "item";

    public KOSPIResponse parseKOSPIResponse(JSONObject jsonObject) {
        JSONObject indexData = getJsonObject(jsonObject);
        return getKospiResponse(indexData);
    }

    public KOSDAQResponse parseKOSDAQResponse(JSONObject jsonObject) {
        return getKosdaqResponse(jsonObject);
    }

    private KOSPIResponse getKospiResponse(final JSONObject indexData) {
        String indexName = indexData.getString(INDEX_NAME);
        String indexValue = indexData.getString(INDEX_VALUE);
        String fluctuationRate = indexData.getString(FLUCTUATION_RATE);

        return new KOSPIResponse(indexName, indexValue, fluctuationRate);
    }

    private KOSDAQResponse getKosdaqResponse(final JSONObject jsonObject) {
        JSONObject indexData = getJsonObject(jsonObject);
        String indexName = indexData.getString(INDEX_NAME);
        String indexValue = indexData.getString(INDEX_VALUE);
        String fluctuationRate = indexData.getString(FLUCTUATION_RATE);

        return new KOSDAQResponse(indexName, indexValue, fluctuationRate);
    }

    private JSONObject getJsonObject(final JSONObject jsonObject) {
        JSONArray items = jsonObject
                .getJSONObject(RESPONSE)
                .getJSONObject(BODY)
                .getJSONObject(ITEMS)
                .getJSONArray(ITEM);
        return items.getJSONObject(0);
    }
}
