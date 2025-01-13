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

    public static final String HTS_KOR_ISNM = "hts_kor_isnm";
    public static final String DATA_RANK = "data_rank";
    public static final String STCK_PRPR = "stck_prpr";
    public static final String PRDY_VRSS = "prdy_vrss";
    public static final String PRDY_VRSS_SIGN = "prdy_vrss_sign";
    public static final String PRDY_CTRT = "prdy_ctrt";
    private final ObjectMapper objectMapper;
    private static final int LIST_SIZE = 5;

    public FluctuationParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<FluctuationDTO> getFluctuation(String responseBody) throws IOException {
        return setJsonNode(responseBody);
    }

    private List<FluctuationDTO> setJsonNode(final String responseBody) throws JsonProcessingException {
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

            String stockName = fluctuationItem.path(HTS_KOR_ISNM).asText();
            int rank = Integer.parseInt(fluctuationItem.path(DATA_RANK).asText());
            int currentPrice = Integer.parseInt(fluctuationItem.path(STCK_PRPR).asText());
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

