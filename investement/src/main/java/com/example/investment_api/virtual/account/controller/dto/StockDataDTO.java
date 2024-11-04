package com.example.investment_api.virtual.account.controller.dto;

import lombok.Getter;

@Getter
public class StockDataDTO {

    private String stockName;
    private String currentPrice;

    public StockDataDTO(final String stockName, final String currentPrice) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
    }
}
