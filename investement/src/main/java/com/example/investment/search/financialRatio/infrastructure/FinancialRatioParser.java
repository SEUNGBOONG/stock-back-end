package com.example.investment.search.financialRatio.infrastructure;

import com.example.investment.search.financialRatio.controller.dto.FinancialRatioDTO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FinancialRatioParser {

    private final ObjectMapper objectMapper;

    public FinancialRatioParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<FinancialRatioDTO> parseFinancialRatio(String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode items = rootNode.path("output");

        List<FinancialRatioDTO> financialRatioList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        int count = 0;

        extractFinancialRatioData(elements, count, financialRatioList);

        return financialRatioList;
    }

    private void extractFinancialRatioData(final Iterator<JsonNode> elements, int count, final List<FinancialRatioDTO> financialRatioList) {
        while (elements.hasNext() && count < 3) {
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
}
