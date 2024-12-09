package com.example.investment_api.virtual.account.domain;

public enum OrderType {

    BUY("매수"),
    SELL("매도");

    private String type;

    OrderType(String type) {
    }

    public String getType() {
        return type;
    }
}
