package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class NoSuchStock extends NoSuchElementException {

    public NoSuchStock(String stockName) {
        super("주식 데이터를 찾을 수 없습니다. " + stockName);
    }
}
