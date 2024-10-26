package com.example.investment_api.search.detail.consensus.domain;

import com.example.investment_api.search.detail.consensus.controller.dto.InvestmentRecommendationDTO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Double.parseDouble;

@Component
public class InvestmentRecommendationParser {

    private final ObjectMapper objectMapper;

    public InvestmentRecommendationParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public InvestmentRecommendationDTO parseInvestmentRecommendation(String responseBody) throws IOException {
        return getInvestmentRecommendationDTO(responseBody);
    }

    private InvestmentRecommendationDTO getInvestmentRecommendationDTO(final String responseBody) throws IOException {
        Iterator<JsonNode> elements = getJsonNodeElements(responseBody);
        return extractInvestmentRecommendationData(elements);
    }

    private Iterator<JsonNode> getJsonNodeElements(final String responseBody) throws IOException {
        JsonNode items = objectMapper.readTree(responseBody).path("output");
        return items.elements();
    }

    private InvestmentRecommendationDTO extractInvestmentRecommendationData(final Iterator<JsonNode> elements) {
        List<Double> targetPrices = new ArrayList<>();
        List<Double> stockPrices = new ArrayList<>();
        List<Double> stockDifferencePrices = new ArrayList<>();

        while (elements.hasNext()) {
            JsonNode investmentRecommendationItems = elements.next();
            appendPricesToLists(targetPrices, stockPrices, stockDifferencePrices, investmentRecommendationItems);
        }

        String latestTargetPrice = formatDoubleAsString(targetPrices.get(targetPrices.size() - 1));
        String stockPrice = formatDoubleAsString(stockPrices.get(0));
        String avgStockDifferencePrice = formatDoubleAsString(calculateAverage(stockDifferencePrices));

        return new InvestmentRecommendationDTO(latestTargetPrice, stockPrice, avgStockDifferencePrice);
    }

    private void appendPricesToLists(List<Double> targetPrices, List<Double> stockPrices, List<Double> stockDifferencePrices, JsonNode investmentRecommendationItems) {
        String targetPriceStr = investmentRecommendationItems.path("hts_goal_prc").asText();
        String stockPriceStr = investmentRecommendationItems.path("stck_prdy_clpr").asText();
        String stockDifferencePriceStr = investmentRecommendationItems.path("stck_nday_esdg").asText();

        if (areValuesValid(targetPriceStr, stockPriceStr, stockDifferencePriceStr)) {
            try {
                double targetPrice = parseDouble(targetPriceStr);
                double stockPrice = parseDouble(stockPriceStr);
                double stockDifferencePrice = parseDouble(stockDifferencePriceStr);

                targetPrices.add(targetPrice);
                stockPrices.add(stockPrice);
                stockDifferencePrices.add(stockDifferencePrice);
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private boolean areValuesValid(String... values) {
        for (String value : values) {
            if (value.isEmpty()) return false;
        }
        return true;
    }

    private double calculateAverage(List<Double> values) {
        if (values.isEmpty()) return 0;
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private String formatDoubleAsString(double value) {
        return String.format("%.2f", value);
    }
}
