package com.example.investment_api.search.base.searchHome.infrastructure;

import com.example.investment_api.search.base.searchHome.dto.StockDataDTO;
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
    public static final String DATA_RANK = "data_rank";
    public static final String STOCK_PRICE = "stck_prpr";
    public static final String STOCK_NAME = "hts_kor_isnm";
    public static final String PREVIOUS_CHANGE = "prdy_vrss";
    public static final String PREVIOUS_CHAGER_RATE = "prdy_ctrt";
    public static final String TRADING_VLOUME = "acml_vol";
    public static final String MARKET_CAPITALIZAITON = "stck_avls";
    public static final String OUTPUT = "output";

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
            String rank = marketCapitalizationOutput.path(DATA_RANK).asText();
            String stockPrice = marketCapitalizationOutput.path(STOCK_PRICE).asText();
            String stockName = marketCapitalizationOutput.path(STOCK_NAME).asText();
            String prevChangePrice = marketCapitalizationOutput.path(PREVIOUS_CHANGE).asText();
            String prevChangeRate = marketCapitalizationOutput.path(PREVIOUS_CHAGER_RATE).asText();
            String tradingVolume = marketCapitalizationOutput.path(TRADING_VLOUME).asText();
            String marketCapitalization = marketCapitalizationOutput.path(MARKET_CAPITALIZAITON).asText();

            marketCapitalizationDTOList.add(new StockDataDTO(rank, stockName, stockPrice, prevChangePrice, prevChangeRate, marketCapitalization, tradingVolume));
        }
    }

    private JsonNode getJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path(OUTPUT);
    }
}
