package com.example.investment.home.marketCapitalization.infrastructure;

import com.example.investment.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class MarketCapitalizationParser {

    private final ObjectMapper objectMapper;

    public MarketCapitalizationParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MarketCapitalizationDTO> parse(String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode items = rootNode.path("output");

        return extractMarketCapitalizationData(items);
    }

    private List<MarketCapitalizationDTO> extractMarketCapitalizationData(final JsonNode items) {
        List<MarketCapitalizationDTO> marketCapitalizationDTOList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        int count = 0;

        while (elements.hasNext() && count < 5) {
            JsonNode marketCapitalizationOutput = elements.next();
            String rank = marketCapitalizationOutput.path("data_rank").asText();
            String stockPrice = marketCapitalizationOutput.path("stck_prpr").asText();
            String stockName = marketCapitalizationOutput.path("hts_kor_isnm").asText();
            String marketCapitalization = marketCapitalizationOutput.path("stck_avls").asText();
            marketCapitalizationDTOList.add(new MarketCapitalizationDTO(rank, stockPrice, stockName, marketCapitalization));
            count++;
        }
        return marketCapitalizationDTOList;
    }

}
