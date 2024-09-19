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
            String rank = marketCapitalizationOutput.path("data_rank").asText();
            String stockPrice = marketCapitalizationOutput.path("stck_prpr").asText();
            String stockName = marketCapitalizationOutput.path("hts_kor_isnm").asText();
            String marketCapitalization = marketCapitalizationOutput.path("stck_avls").asText();

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
