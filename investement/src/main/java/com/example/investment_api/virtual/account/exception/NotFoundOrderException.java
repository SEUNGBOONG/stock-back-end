package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class NotFoundOrderException extends NoSuchElementException {
    public NotFoundOrderException() {
        super("주문을 찾지 못합니다.");
    }
}
