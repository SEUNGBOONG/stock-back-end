package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class AccountAndStockNotFoundException extends NoSuchElementException {
    public AccountAndStockNotFoundException(Long memberId, String stockName) {
        super("계좌에서 " + memberId + "아이디를 찾을 수 없습니다." + stockName+" 주식 이름을 찾을 수 없습니다.");
    }
}
