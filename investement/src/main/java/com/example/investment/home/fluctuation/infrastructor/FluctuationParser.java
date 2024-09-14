package com.example.investment.home.fluctuation.infrastructor;

import com.example.investment.home.fluctuation.controller.dto.response.FluctuationDTO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FluctuationParser {

    private final ObjectMapper objectMapper;
    private static final int LIST_SIZE = 5;

    public FluctuationParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<FluctuationDTO> getFluctuation(String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode items = rootNode.path("output");

        List<FluctuationDTO> fluctuationDTOList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        buildDataList(fluctuationDTOList, elements);
        return fluctuationDTOList;
    }

    private void buildDataList(List<FluctuationDTO> fluctuationDTOList, Iterator<JsonNode> elements) {
        int count = 0;
        while (isUnderLimit(elements, count)) {
            JsonNode fluctuationItem = elements.next();

            String stockName = fluctuationItem.path("hts_kor_isnm").asText();
            int rank = Integer.parseInt(fluctuationItem.path("data_rank").asText());
            int currentPrice = Integer.parseInt(fluctuationItem.path("stck_prpr").asText());
            int prevChangePrice = Integer.parseInt(fluctuationItem.path("prdy_vrss").asText());
            String prevSign = fluctuationItem.path("prdy_vrss_sign").asText();
            Double prevChangeRate = Double.valueOf(fluctuationItem.path("prdy_ctrt").asText());

            fluctuationDTOList.add(new FluctuationDTO(stockName, rank, currentPrice, prevChangePrice, prevSign, prevChangeRate));
            count++;
        }
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return count < LIST_SIZE && elements.hasNext();
    }
}

