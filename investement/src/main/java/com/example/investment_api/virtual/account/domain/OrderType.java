package com.example.investment_api.virtual.account.domain;

import lombok.Getter;

@Getter
public enum OrderType {

    BUY("매수"),
    SELL("매도");

    private final String type;

    OrderType(String type) {
        this.type = type;
    }
}
