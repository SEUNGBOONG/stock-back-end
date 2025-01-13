package com.example.investment_api.virtual.account.infrastructure;

import com.example.investment_api.virtual.account.controller.dto.StockDataDTO;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccountStockParser {

    public static final String STOCK_NAME = "bstp_kor_isnm";
    public static final String STOCK_PRICE = "stck_prpr";
    public static final String OUTPUT = "output";
    private final ObjectMapper objectMapper;

    public AccountStockParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public StockDataDTO parse(String responseBody) throws IOException {
        JsonNode stockItem = setJson(responseBody);
        return toDTO(stockItem);
    }

    private JsonNode setJson(final String responseBody) throws JsonProcessingException {
        return objectMapper.readTree(responseBody).path(OUTPUT);
    }

    private StockDataDTO toDTO(final JsonNode stockItem) {
        String stockName = stockItem.path(STOCK_NAME).asText();
        String stockPrice = stockItem.path(STOCK_PRICE).asText();
        return new StockDataDTO(stockName, stockPrice);
    }
}
