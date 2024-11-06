package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class StockNotFoundException extends NoSuchElementException {

    public StockNotFoundException() {
        super("주식 데이터를 찾을 수 없습니다.");
    }
}
