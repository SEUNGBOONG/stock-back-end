package com.example.investment_api.member.exception.exceptions.auth;

import com.example.investment_api.member.exception.exceptions.MemberErrorCode;
import com.example.investment_api.member.exception.exceptions.MemberException;

public class NotSamePasswordException extends MemberException {

    public NotSamePasswordException() {
        super(MemberErrorCode.NOT_SAME_PASSWORD);
    }
}
