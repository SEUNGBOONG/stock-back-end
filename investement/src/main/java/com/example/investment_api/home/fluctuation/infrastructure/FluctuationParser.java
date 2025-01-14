package com.example.investment_api.home.fluctuation.infrastructure;

import com.example.investment_api.home.fluctuation.controller.dto.response.FluctuationDTO;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FluctuationParser {

    private static final String PRDY_VRSS = "prdy_vrss";
    private static final String PRDY_VRSS_SIGN = "prdy_vrss_sign";
    private static final String PRDY_CTRT = "prdy_ctrt";
    private static final String DATA_RANK = "data_rank";
    private static final String STOCK_PRICE = "stck_prpr";
    private static final String STOCK_NAME = "hts_kor_isnm";
    private static final String OUTPUT = "output";

    private static final int LIST_SIZE = 5;

    private final ObjectMapper objectMapper;

    public FluctuationParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<FluctuationDTO> getFluctuation(String responseBody) throws IOException {
        return setJsonNode(responseBody);
    }

    private List<FluctuationDTO> setJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode items = rootNode.path(OUTPUT);

        List<FluctuationDTO> fluctuationDTOList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        buildDataList(fluctuationDTOList, elements);
        return fluctuationDTOList;
    }

    private void buildDataList(List<FluctuationDTO> fluctuationDTOList, Iterator<JsonNode> elements) {
        int count = 0;
        while (isUnderLimit(elements, count)) {
            JsonNode fluctuationItem = elements.next();

            String stockName = fluctuationItem.path(STOCK_NAME).asText();
            int rank = Integer.parseInt(fluctuationItem.path(DATA_RANK).asText());
            int currentPrice = Integer.parseInt(fluctuationItem.path(STOCK_PRICE).asText());
            int prevChangePrice = Integer.parseInt(fluctuationItem.path(PRDY_VRSS).asText());
            String prevSign = fluctuationItem.path(PRDY_VRSS_SIGN).asText();
            Double prevChangeRate = Double.valueOf(fluctuationItem.path(PRDY_CTRT).asText());

            fluctuationDTOList.add(new FluctuationDTO(stockName, rank, currentPrice, prevChangePrice, prevSign, prevChangeRate));
            count++;
        }
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return count < LIST_SIZE && elements.hasNext();
    }
}

