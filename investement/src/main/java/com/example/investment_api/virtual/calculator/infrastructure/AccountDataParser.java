package com.example.investment_api.virtual.calculator.infrastructure;

import com.example.investment_api.virtual.account.controller.dto.StockData;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDataParser {

    public static final String STOCK_NAME = "hts_kor_isnm";
    public static final String STOCK_PRICE = "stck_prpr";
    public static final String PREVIOUS_CHANGE_RATE = "prdy_ctrt";
    public static final String OUTPUT = "output";
    private final ObjectMapper objectMapper;

    public AccountDataParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<StockData> parseAll(String responseBody) throws IOException{
        JsonNode items = getJsonNode(responseBody);
        List<StockData> stockDataList = new ArrayList<>();

        for (JsonNode item : items) {
            StockData stockData = extractStockData(item);
            stockDataList.add(stockData);
        }

        return stockDataList;
    }

    private StockData extractStockData(final JsonNode item) {
        String stockName = item.path(STOCK_NAME).asText();  // 주식 이름
        int stockPrice = item.path(STOCK_PRICE).asInt();        // 현재 주가
        double prevChangeRate = item.path(PREVIOUS_CHANGE_RATE).asDouble(); // 전일 대비 등락률

        return new StockData(stockName, stockPrice, prevChangeRate);
    }

    private JsonNode getJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path(OUTPUT);
    }
}
