package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class NoSuchAccountMemberId extends NoSuchElementException {
    public NoSuchAccountMemberId(Long memberId, String stockName) {
        super("계좌에서 아이디를 찾지 못합니다." + memberId + "주식 이름도 찾지 못합니다" + stockName);
    }
}
