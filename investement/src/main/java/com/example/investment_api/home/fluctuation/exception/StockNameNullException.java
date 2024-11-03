package com.example.investment_api.home.fluctuation.exception;

public class StockNameNullException extends Throwable {
    public StockNameNullException() {
        super("주식 이름을 입력해주세요");
    }
}
