package com.example.investment_api.search.detail.financialRatio.infrastructure;

import com.example.investment_api.search.detail.financialRatio.controller.dto.FinancialRatioDTO;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FinancialRatioParser {

    private static final int LIST_SIZE = 3;

    private final ObjectMapper objectMapper;

    public FinancialRatioParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<FinancialRatioDTO> parseFinancialRatio(String responseBody) throws IOException {
        return setJsonNode(responseBody);
    }

    private List<FinancialRatioDTO> setJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode items = objectMapper.readTree(responseBody).path("output");

        List<FinancialRatioDTO> financialRatioList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();

        extractFinancialRatioData(elements, financialRatioList);
        return financialRatioList;
    }

    private void extractFinancialRatioData(final Iterator<JsonNode> elements, final List<FinancialRatioDTO> financialRatioList) {
        int count = 0;

        while (isUnderLimit(elements, count)) {
            JsonNode ratioItem = elements.next();

            String stockAccountingYearMonth = ratioItem.path("stac_yymm").asText();
            String grossMarginRatio = ratioItem.path("grs").asText();
            String businessProfitRate = ratioItem.path("bsop_prfi_inrt").asText();
            String netInterestRate = ratioItem.path("ntin_inrt").asText();
            String roeValue = ratioItem.path("roe_val").asText();
            String earningsPerShare = ratioItem.path("eps").asText();
            String salesPerShare = ratioItem.path("sps").asText();
            String bookValuePerShare = ratioItem.path("bps").asText();
            String reserveRate = ratioItem.path("rsrv_rate").asText();
            String liabilityRate = ratioItem.path("lblt_rate").asText();

            financialRatioList.add(new FinancialRatioDTO(stockAccountingYearMonth, grossMarginRatio, businessProfitRate, netInterestRate, roeValue, earningsPerShare, salesPerShare, bookValuePerShare, reserveRate, liabilityRate));
            count++;
        }
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return elements.hasNext() && count < LIST_SIZE;
    }
}
