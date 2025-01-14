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

    public List<StockData> parseAll(String responseBody) throws IOException {
        JsonNode items = parseResponseBody(responseBody);
        return convertToStockDataList(items);
    }

    private JsonNode parseResponseBody(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path(OUTPUT);
    }

    private List<StockData> convertToStockDataList(JsonNode items) {
        List<StockData> stockDataList = new ArrayList<>();
        for (JsonNode item : items) {
            stockDataList.add(extractStockData(item));
        }
        return stockDataList;
    }

    private StockData extractStockData(final JsonNode item) {
        String stockName = extractFieldAsString(item);
        int stockPrice = extractFieldAsInt(item);
        double prevChangeRate = extractFieldAsDouble(item);

        return new StockData(stockName, stockPrice, prevChangeRate);
    }

    private String extractFieldAsString(JsonNode item) {
        return item.path(AccountDataParser.STOCK_NAME).asText();
    }

    private int extractFieldAsInt(JsonNode item) {
        return item.path(AccountDataParser.STOCK_PRICE).asInt();
    }

    private double extractFieldAsDouble(JsonNode item) {
        return item.path(AccountDataParser.PREVIOUS_CHANGE_RATE).asDouble();
    }
}
