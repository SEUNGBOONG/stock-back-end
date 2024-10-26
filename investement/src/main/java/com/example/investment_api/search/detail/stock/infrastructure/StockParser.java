package com.example.investment_api.search.detail.stock.infrastructure;

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
        String stockName = stockItem.path("bstp_kor_isnm").asText();
        String stockPrice = stockItem.path("stck_prpr").asText();
        String previousStockPrice = stockItem.path("prdy_vrss").asText();
        String contrastRatio = stockItem.path("prdy_ctrt").asText();
        String highStockPrice = stockItem.path("stck_hgpr").asText();
        String lowStockPrice = stockItem.path("stck_lwpr").asText();

        return new StockResponse(stockName, stockPrice, previousStockPrice, contrastRatio, highStockPrice, lowStockPrice);
    }

}
