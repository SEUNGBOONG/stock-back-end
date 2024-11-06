package com.example.investment_api.virtual.account.infrastructure;

import com.example.investment_api.virtual.account.controller.dto.StockDataDTO;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccountStockParser {

    private final ObjectMapper objectMapper;

    public AccountStockParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public StockDataDTO parse(String responseBody) throws IOException {
        JsonNode stockItem = setJson(responseBody);
        return toDTO(stockItem);
    }

    private JsonNode setJson(final String responseBody) throws JsonProcessingException {
        return objectMapper.readTree(responseBody).path("output");
    }

    private StockDataDTO toDTO(final JsonNode stockItem) {
        String stockName = stockItem.path("bstp_kor_isnm").asText();
        String stockPrice = stockItem.path("stck_prpr").asText();
        return new StockDataDTO(stockName, stockPrice);
    }
}
