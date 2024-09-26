package com.example.investment_api.member.exception.exceptions.member;

import com.example.investment_api.member.exception.exceptions.MemberErrorCode;
import com.example.investment_api.member.exception.exceptions.MemberException;

public class DuplicateNickNameException extends MemberException {

    public DuplicateNickNameException() {
        super(MemberErrorCode.DUPLICATED_NICK_NAME);
    }
}
