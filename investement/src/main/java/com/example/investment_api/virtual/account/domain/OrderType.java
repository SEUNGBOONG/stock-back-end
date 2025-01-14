package com.example.investment_api.virtual.account.domain;

<<<<<<< HEAD
import lombok.Getter;

@Getter
=======
>>>>>>> origin/171/refactroing/reCode
public enum OrderType {

    BUY("매수"),
    SELL("매도");

<<<<<<< HEAD
    private final String type;

    OrderType(String type) {
        this.type = type;
=======
    private String type;

    OrderType(String type) {
    }

    public String getType() {
        return type;
>>>>>>> origin/171/refactroing/reCode
    }
}
