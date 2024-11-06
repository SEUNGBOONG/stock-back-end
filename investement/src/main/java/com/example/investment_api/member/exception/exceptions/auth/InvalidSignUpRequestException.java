package com.example.investment_api.member.exception.exceptions.auth;

import com.example.investment_api.member.exception.exceptions.MemberErrorCode;
import com.example.investment_api.member.exception.exceptions.MemberException;

public class InvalidSignUpRequestException extends MemberException {
    public InvalidSignUpRequestException(){
        super(MemberErrorCode.INVALID_SIGNUP_REQUEST);
    }
}
