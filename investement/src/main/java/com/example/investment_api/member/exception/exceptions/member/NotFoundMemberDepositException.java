package com.example.investment_api.member.exception.exceptions.member;

import com.example.investment_api.member.exception.exceptions.MemberErrorCode;
import com.example.investment_api.member.exception.exceptions.MemberException;

public class NotFoundMemberDepositException extends MemberException {

    public NotFoundMemberDepositException() {
        super(MemberErrorCode.NOT_FOUND_MEMBER_DEPOSIT);
    }
}
