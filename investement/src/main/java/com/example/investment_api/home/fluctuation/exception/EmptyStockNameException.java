package com.example.investment_api.home.fluctuation.exception;

public class EmptyStockNameException extends Throwable {
    public EmptyStockNameException() {
        super("주식 이름을 입력해주세요");
    }
}
