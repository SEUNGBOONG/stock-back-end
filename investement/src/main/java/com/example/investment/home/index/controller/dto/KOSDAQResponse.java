package com.example.investment.home.index.controller.dto;

public record KOSDAQResponse(
        String indexName,
        String indexValue,
        String fluctuationRate
) {
}
