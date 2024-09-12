package com.example.investment.tradingVolume.infrastructure;

import com.example.investment.tradingVolume.controller.dto.TradingVolumeDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class TradingVolumeParser {

    private final ObjectMapper objectMapper;

    public TradingVolumeParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<TradingVolumeDTO> getTradingVolume(String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode items = rootNode.path("output");

        List<TradingVolumeDTO> tradingVolumeList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        int count = 0;

        while (elements.hasNext() && count < 5) {
            JsonNode tradingVolumeItem = elements.next();

            String stockName = tradingVolumeItem.path("hts_kor_isnm").asText();
            String rank = tradingVolumeItem.path("data_rank").asText();
            String currentPrice = tradingVolumeItem.path("stck_prpr").asText();
            String totalVolume = tradingVolumeItem.path("acml_vol").asText();
            String prevVolume = tradingVolumeItem.path("prdy_vol").asText();
            String volumeChangeRate = tradingVolumeItem.path("vol_inrt").asText();

            tradingVolumeList.add(new TradingVolumeDTO(stockName, rank, currentPrice, totalVolume, prevVolume, volumeChangeRate));
            count++;
        }

        return tradingVolumeList;
    }
}
