package com.example.investment_api.home.fluctuation.infrastructure;

import com.example.investment_api.common.api.ApiMessage;
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

    private final ObjectMapper objectMapper;

    public static final String OUTPUT = "output";
    private static final int LIST_SIZE = 5;

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

            String stockName = fluctuationItem.path(ApiMessage.STOCK_NAME.name()).asText();
            int rank = Integer.parseInt(fluctuationItem.path(ApiMessage.DATA_RANK.name()).asText());
            int currentPrice = Integer.parseInt(fluctuationItem.path(ApiMessage.STOCK_PREV.name()).asText());
            int prevChangePrice = Integer.parseInt(fluctuationItem.path(ApiMessage.PREV_CHANGE_PRICE.name()).asText());
            String prevSign = fluctuationItem.path(ApiMessage.PREV_SIGN.name()).asText();
            Double prevChangeRate = Double.valueOf(fluctuationItem.path(ApiMessage.PREV_CHANGE_RATE.name()).asText());

            fluctuationDTOList.add(new FluctuationDTO(stockName, rank, currentPrice, prevChangePrice, prevSign, prevChangeRate));
            count++;
        }
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return count < LIST_SIZE && elements.hasNext();
    }
}

