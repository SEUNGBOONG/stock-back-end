package com.example.investment_api.home.index.controller.dto;

public record KOSPIResponse(
        String indexName,
        String indexValue,
        String fluctuationRate
) {
    @Override
    public String toString() {
        return String.format("{\"indexName\":\"%s\", \"indexValue\":\"%s\", \"fluctuationRate\":\"%s\"}",
                indexName, indexValue, fluctuationRate);
    }

}
