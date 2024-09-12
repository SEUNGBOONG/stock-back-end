package com.example.investment.home.marketCapitalization.infrastructure;

import com.example.investment.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public List<MarketCapitalizationDTO> getTradingVolume(String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode items = rootNode.path("output");
        List<MarketCapitalizationDTO> marketCapitalizationList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        int count = 0;
        extractMarketCapitalizationData(elements, count, marketCapitalizationList);

        return marketCapitalizationList;
    }

    private static void extractMarketCapitalizationData(final Iterator<JsonNode> elements, int count, final List<MarketCapitalizationDTO> marketCapitalizationList) {
        while (elements.hasNext() && count < 5) {
            JsonNode marketCapitalizationItems = elements.next();

            String stockName = marketCapitalizationItems.path("hts_kor_isnm").asText();
            String marketCapitalization = marketCapitalizationItems.path("stck_avls").asText();

            marketCapitalizationList.add(new MarketCapitalizationDTO(stockName, marketCapitalization));
            count++;
        }
    }
}
