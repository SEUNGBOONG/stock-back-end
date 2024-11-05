package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class NoSuchMemberIdException extends NoSuchElementException {
    public NoSuchMemberIdException(Long memberId) {
        super("아이디를 찾지 못합니다.: " + memberId);
    }
}
