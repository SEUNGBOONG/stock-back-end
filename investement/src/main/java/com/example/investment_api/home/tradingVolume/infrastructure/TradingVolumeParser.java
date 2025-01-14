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
    public static final String STOCK_NAME = "hts_kor_isnm";
    public static final String DATA_RANK = "data_rank";
    public static final String STOCK_PRICE = "stck_prpr";
    public static final String PREVIOUS_VOLUME = "prdy_vol";
    public static final String VOLUME_CHANGE_RATE = "vol_inrt";
    public static final String VOLUME = "acml_vol";
    public static final String OUTPUT = "output";

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

            String stockName = tradingVolumeItem.path(STOCK_NAME).asText();
            String rank = tradingVolumeItem.path(DATA_RANK).asText();
            String currentPrice = tradingVolumeItem.path(STOCK_PRICE).asText();
            String totalVolume = tradingVolumeItem.path(VOLUME).asText();
            String prevVolume = tradingVolumeItem.path(PREVIOUS_VOLUME).asText();
            String volumeChangeRate = tradingVolumeItem.path(VOLUME_CHANGE_RATE).asText();

            tradingVolumeList.add(new TradingVolumeDTO(stockName, rank, currentPrice, totalVolume, prevVolume, volumeChangeRate));
            count++;
        }
    }

    private JsonNode setJsonNOde(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path(OUTPUT);
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return elements.hasNext() && count < LIST_SIZE;
    }
}
