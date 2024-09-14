package com.example.investment.search.consensus.infrastructure;

import com.example.investment.search.consensus.controller.dto.InvestmentRecommendationDTO;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class InvestmentRecommendationParser {

    private final ObjectMapper objectMapper;

    public InvestmentRecommendationParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public InvestmentRecommendationDTO parseInvestmentRecommendation(String responseBody) throws IOException {
        Iterator<JsonNode> elements = getJsonNodeElements(responseBody);
        return extractInvestmentRecommendationData(elements);
    }

    private Iterator<JsonNode> getJsonNodeElements(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode items = rootNode.path("output");
        Iterator<JsonNode> elements = items.elements();
        return elements;
    }

    private InvestmentRecommendationDTO extractInvestmentRecommendationData(final Iterator<JsonNode> elements) {
        List<Double> targetPrices = new ArrayList<>();
        List<Double> stockPrices = new ArrayList<>();
        List<Double> stockDifferencePrices = new ArrayList<>();

        while (elements.hasNext()) {
            JsonNode investmentRecommendationItems = elements.next();
            appendPricesToLists(targetPrices, stockPrices, stockDifferencePrices, investmentRecommendationItems);
        }

        return createRecommendDataDTO(targetPrices, stockPrices, stockDifferencePrices);
    }

    private void appendPricesToLists(List<Double> targetPrices, List<Double> stockPrices, List<Double> stockDifferencePrices, JsonNode investmentRecommendationItems) {
        String targetPriceStr = investmentRecommendationItems.path("hts_goal_prc").asText();
        String stockPriceStr = investmentRecommendationItems.path("stck_prdy_clpr").asText();
        String stockDifferencePriceStr = investmentRecommendationItems.path("stck_nday_esdg").asText();

        if (areValuesValid(targetPriceStr, stockPriceStr, stockDifferencePriceStr)) {
            try {
                double targetPrice = Double.parseDouble(targetPriceStr);
                double stockPrice = Double.parseDouble(stockPriceStr);
                double stockDifferencePrice = Double.parseDouble(stockDifferencePriceStr);

                targetPrices.add(targetPrice);
                stockPrices.add(stockPrice);
                stockDifferencePrices.add(stockDifferencePrice);
            } catch (NumberFormatException e) {
            }
        }
    }

    private boolean areValuesValid(String... values) {
        for (String value : values) {
            if (value.isEmpty()) return false;
        }
        return true;
    }

    private InvestmentRecommendationDTO createRecommendDataDTO(List<Double> targetPrices, List<Double> stockPrices, List<Double> stockDifferencePrices) {
        String avgTargetPrice = formatDoubleAsString(calculateAverage(targetPrices));
        String avgStockPrice = formatDoubleAsString(calculateAverage(stockPrices));
        String avgStockDifferencePrice = formatDoubleAsString(calculateAverage(stockDifferencePrices));

        return new InvestmentRecommendationDTO(avgTargetPrice, avgStockPrice, avgStockDifferencePrice);
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
