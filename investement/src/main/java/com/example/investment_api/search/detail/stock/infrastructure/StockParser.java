package com.example.investment_api.search.detail.stock.infrastructure;

import com.example.investment_api.common.api.ApiMessage;
import com.example.investment_api.search.detail.stock.controller.dto.StockResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StockParser {

    private final ObjectMapper objectMapper;

    public StockParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public StockResponse parse(String responseBody) throws IOException {
        JsonNode stockItem = setJson(responseBody);
        return toDTO(stockItem);
    }

    private JsonNode setJson(final String responseBody) throws JsonProcessingException {
        return objectMapper.readTree(responseBody).path("output");
    }

    private StockResponse toDTO(final JsonNode stockItem) {
        String stockName = stockItem.path(ApiMessage.STOCK_NAME.name()).asText();
        String stockPrice = stockItem.path(ApiMessage.STOCK_PREV.name()).asText();
        String previousStockPrice = stockItem.path(ApiMessage.PREV_CHANGE_PRICE.name()).asText();
        String contrastRatio = stockItem.path(ApiMessage.PREV_CHANGE_RATE.name()).asText();
        String highStockPrice = stockItem.path(ApiMessage.HIGH_STOCK_PRICE.name()).asText();
        String lowStockPrice = stockItem.path(ApiMessage.LOW_STOCK_PRICE.name()).asText();

        return new StockResponse(stockName, stockPrice, previousStockPrice, contrastRatio, highStockPrice, lowStockPrice);
    }

}
