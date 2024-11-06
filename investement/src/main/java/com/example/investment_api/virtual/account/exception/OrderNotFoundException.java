package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class OrderNotFoundException extends NoSuchElementException {
    public OrderNotFoundException() {
        super("주문을 찾을 수 없습니다.");
    }
}
