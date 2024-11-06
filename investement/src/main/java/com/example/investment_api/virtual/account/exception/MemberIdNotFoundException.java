package com.example.investment_api.virtual.account.exception;

import java.util.NoSuchElementException;

public class MemberIdNotFoundException extends NoSuchElementException {
    public MemberIdNotFoundException(Long memberId) {
        super(memberId+ " 아이디를 찾을 수 없습니다.");
    }
}
