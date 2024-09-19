package com.example.investment_api.home.tradingVolume.infrastructure;

import com.example.investment_api.home.tradingVolume.controller.dto.TradingVolumeDTO;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class TradingVolumeParser {

    private static final int LIST_SIZE = 5;

    private final ObjectMapper objectMapper;

    public TradingVolumeParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<TradingVolumeDTO> getTradingVolume(String responseBody) throws IOException {
        JsonNode items = setJsonNOde(responseBody);
        return extractTradingVolumeData(items);
    }

    private List<TradingVolumeDTO> extractTradingVolumeData(final JsonNode items) {
        List<TradingVolumeDTO> tradingVolumeList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        buildDataList(tradingVolumeList, elements);
        return tradingVolumeList;

    }

    private void buildDataList(List<TradingVolumeDTO> tradingVolumeList, Iterator<JsonNode> elements) {
        int count = 0;
        while (isUnderLimit(elements, count)) {
            JsonNode tradingVolumeItem = elements.next();

            String stockName = tradingVolumeItem.path("hts_kor_isnm").asText();
            String rank = tradingVolumeItem.path("data_rank").asText();
            String currentPrice = tradingVolumeItem.path("stck_prpr").asText();
            String totalVolume = tradingVolumeItem.path("acml_vol").asText();
            String prevVolume = tradingVolumeItem.path("prdy_vol").asText();
            String volumeChangeRate = tradingVolumeItem.path("vol_inrt").asText();

            tradingVolumeList.add(new TradingVolumeDTO(stockName, rank, currentPrice, totalVolume, prevVolume, volumeChangeRate));
            count++;
        }
    }

    private JsonNode setJsonNOde(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path("output");
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return elements.hasNext() && count < LIST_SIZE;
    }
}
