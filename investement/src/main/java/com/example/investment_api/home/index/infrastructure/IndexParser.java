package com.example.investment_api.home.index.infrastructure;

import com.example.investment_api.common.api.ApiMessage;
import com.example.investment_api.home.index.controller.dto.KOSDAQResponse;

import com.example.investment_api.home.index.controller.dto.KOSPIResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.stereotype.Component;

@Component
public class IndexParser {

    public KOSPIResponse parseKOSPIResponse(JSONObject jsonObject) {
        JSONObject indexData = getJsonObject(jsonObject);
        return getKospiResponse(indexData);
    }

    public KOSDAQResponse parseKOSDAQResponse(JSONObject jsonObject) {
        return getKosdaqResponse(jsonObject);
    }

    private KOSPIResponse getKospiResponse(final JSONObject indexData) {
        String indexName = indexData.getString(ApiMessage.STOCK_NAME.name());
        String indexValue = indexData.getString(ApiMessage.INDEX_VALUE.name());
        String fluctuationRate = indexData.getString(ApiMessage.FLUCTUATION_RATE.name());

        return new KOSPIResponse(indexName, indexValue, fluctuationRate);
    }

    private KOSDAQResponse getKosdaqResponse(final JSONObject jsonObject) {
        JSONObject indexData = getJsonObject(jsonObject);
        String indexName = indexData.getString(ApiMessage.STOCK_NAME.name());
        String indexValue = indexData.getString(ApiMessage.INDEX_VALUE.name());
        String fluctuationRate = indexData.getString(ApiMessage.FLUCTUATION_RATE.name());

        return new KOSDAQResponse(indexName, indexValue, fluctuationRate);
    }

    private JSONObject getJsonObject(final JSONObject jsonObject) {
        JSONArray items = jsonObject
                .getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");
        return items.getJSONObject(0);
    }
}
