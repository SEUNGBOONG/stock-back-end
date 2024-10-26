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
            String rank = marketCapitalizationOutput.path("data_rank").asText();
            String stockPrice = marketCapitalizationOutput.path("stck_prpr").asText();
            String stockName = marketCapitalizationOutput.path("hts_kor_isnm").asText();
            String prevChangePrice = marketCapitalizationOutput.path("prdy_vrss").asText();
            String prevChangeRate = marketCapitalizationOutput.path("prdy_ctrt").asText();
            String tradingVolume = marketCapitalizationOutput.path("acml_vol").asText();
            String marketCapitalization = marketCapitalizationOutput.path("stck_avls").asText();

            marketCapitalizationDTOList.add(new StockDataDTO(rank, stockName, stockPrice, prevChangePrice, prevChangeRate, marketCapitalization, tradingVolume));
        }
    }

    private JsonNode getJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path("output");
    }
}
