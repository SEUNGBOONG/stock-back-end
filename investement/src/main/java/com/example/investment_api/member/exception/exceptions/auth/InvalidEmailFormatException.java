package com.example.investment_api.member.exception.exceptions.auth;

import com.example.investment_api.member.exception.exceptions.MemberErrorCode;
import com.example.investment_api.member.exception.exceptions.MemberException;

public class InvalidEmailFormatException extends MemberException {
    public InvalidEmailFormatException() {
        super(MemberErrorCode.INVALID_EMAIL);
    }
}
