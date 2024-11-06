package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class AccountNotFoundException extends NoSuchElementException {
    public AccountNotFoundException() {
        super("계좌를 찾을 수 없습니다.");
    }
}
