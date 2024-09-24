package com.example.investment_api.home.index.controller.dto;

public record KOSDAQResponse(
        String indexName,
        String indexValue,
        String fluctuationRate
) {
    @Override
    public String toString() {
        return String.format("{\"지수이름\":\"%s\", \"지수값\":\"%s\", \"등락률\":\"%s\"}",
                indexName, indexValue, fluctuationRate);
    }

}
