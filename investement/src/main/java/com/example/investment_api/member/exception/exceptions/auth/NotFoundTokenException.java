package com.example.investment_api.member.exception.exceptions.auth;

import com.example.investment_api.member.exception.exceptions.MemberErrorCode;
import com.example.investment_api.member.exception.exceptions.MemberException;

public class NotFoundTokenException extends MemberException {

    public NotFoundTokenException() {
        super(MemberErrorCode.NOT_FOUND_TOKEN_INFORMATION);
    }
}
