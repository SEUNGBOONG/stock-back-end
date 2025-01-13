package com.example.investment_api.home.marketCapitalization.infrastructure;

import com.example.investment_api.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class MarketCapitalizationParser {

    private static final int LIST_SIZE = 5;
    public static final String DATA_RANK = "data_rank";
    public static final String STOCK_PRICE = "stck_prpr";
    public static final String STOCK_NAME = "hts_kor_isnm";
    public static final String MARKET_CAPITALIZATION = "stck_avls";
    private final ObjectMapper objectMapper;

    public MarketCapitalizationParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MarketCapitalizationDTO> parse(String responseBody) throws IOException {
        JsonNode items = getJsonNode(responseBody);
        return extractMarketCapitalizationData(items);
    }

    private List<MarketCapitalizationDTO> extractMarketCapitalizationData(final JsonNode items) {
        List<MarketCapitalizationDTO> marketCapitalizationDTOList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();

        buildDataList(marketCapitalizationDTOList, elements);
        return marketCapitalizationDTOList;
    }

    private void buildDataList(List<MarketCapitalizationDTO> marketCapitalizationDTOList, Iterator<JsonNode> elements) {
        int count = 0;

        while (isUnderLimit(elements, count)) {
            JsonNode marketCapitalizationOutput = elements.next();
            String rank = marketCapitalizationOutput.path(DATA_RANK).asText();
            String stockPrice = marketCapitalizationOutput.path(STOCK_PRICE).asText();
            String stockName = marketCapitalizationOutput.path(STOCK_NAME).asText();
            String marketCapitalization = marketCapitalizationOutput.path(MARKET_CAPITALIZATION).asText();

            marketCapitalizationDTOList.add(new MarketCapitalizationDTO(rank, stockPrice, stockName, marketCapitalization));
            count++;
        }
    }

    private JsonNode getJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path("output");
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return elements.hasNext() && count < LIST_SIZE;
    }
}
