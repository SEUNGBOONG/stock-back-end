package com.example.investment_api.search.base.searchHome.infrastructure;

import com.example.investment_api.common.api.ApiMessage;
import com.example.investment_api.search.base.searchHome.controller.dto.StockDataDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class StockDataParser {
    private final ObjectMapper objectMapper;

    public StockDataParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<StockDataDTO> parse(String responseBody) throws IOException {
        return getStockDataDTOS(responseBody);
    }

    private List<StockDataDTO> getStockDataDTOS(final String responseBody) throws JsonProcessingException {
        JsonNode items = getJsonNode(responseBody);
        return extractStockData(items);
    }

    private List<StockDataDTO> extractStockData(final JsonNode items) {
        List<StockDataDTO> stockDataList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();

        buildDataList(stockDataList, elements);
        return stockDataList;
    }

    private void buildDataList(List<StockDataDTO> marketCapitalizationDTOList, Iterator<JsonNode> elements) {
        while (elements.hasNext()) {
            JsonNode marketCapitalizationOutput = elements.next();
            String rank = marketCapitalizationOutput.path(ApiMessage.DATA_RANK.name()).asText();
            String stockPrice = marketCapitalizationOutput.path(ApiMessage.STOCK_PREV.name()).asText();
            String stockName = marketCapitalizationOutput.path(ApiMessage.STOCK_NAME.name()).asText();
            String prevChangePrice = marketCapitalizationOutput.path(ApiMessage.PREV_CHANGE_PRICE.name()).asText();
            String prevChangeRate = marketCapitalizationOutput.path(ApiMessage.PREV_CHANGE_RATE.name()).asText();
            String tradingVolume = marketCapitalizationOutput.path(ApiMessage.TRADING_VOLUME.name()).asText();
            String marketCapitalization = marketCapitalizationOutput.path(ApiMessage.MARKET_CAPITAILIZATION.name()).asText();

            marketCapitalizationDTOList.add(new StockDataDTO(rank, stockName, stockPrice, prevChangePrice, prevChangeRate, marketCapitalization, tradingVolume));
        }
    }

    private JsonNode getJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path("output");
    }
}
