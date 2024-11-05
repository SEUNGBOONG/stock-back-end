package com.example.investment_api.virtual.account.controller.dto;

import lombok.Getter;

@Getter
public class StockOrderDTO {
    private String stockName;
    private int quantity;
    private int price; // 지정가의 경우 가격

    public StockOrderDTO(String stockName, int quantity, int price) {
        this.stockName = stockName;
        this.quantity = quantity;
        this.price = price;
    }

    public String stockName() {
        return stockName;
    }

    public int quantity() {
        return quantity;
    }

    public int price() {
        return price;
    }
}
