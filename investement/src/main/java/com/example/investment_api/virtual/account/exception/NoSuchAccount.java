package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class NoSuchAccount extends NoSuchElementException {
    public NoSuchAccount() {
        super("계좌를 찾을 수 없습니다.");
    }
}
