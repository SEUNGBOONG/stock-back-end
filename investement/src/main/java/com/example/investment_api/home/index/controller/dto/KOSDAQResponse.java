package com.example.investment_api.home.index.controller.dto;

public record KOSDAQResponse(
        String indexName,
        String indexValue,
        String fluctuationRate
) {
}
