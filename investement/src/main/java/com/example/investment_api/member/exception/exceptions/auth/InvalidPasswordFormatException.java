package com.example.investment_api.member.exception.exceptions.auth;

import com.example.investment_api.member.exception.exceptions.MemberErrorCode;
import com.example.investment_api.member.exception.exceptions.MemberException;

public class InvalidPasswordFormatException extends MemberException {
    public InvalidPasswordFormatException(){
        super(MemberErrorCode.INVALID_PASSWORD);
    }
}
