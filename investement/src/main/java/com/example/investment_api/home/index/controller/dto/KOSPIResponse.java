package com.example.investment_api.home.index.controller.dto;

public record KOSPIResponse(
        String indexName,
        String indexValue,
        String fluctuationRate
) {

}
