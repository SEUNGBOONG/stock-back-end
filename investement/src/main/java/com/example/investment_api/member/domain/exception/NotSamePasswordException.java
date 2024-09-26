package com.example.investment_api.member.domain.exception;

import com.example.investment_api.global.exception.exceptions.CustomErrorCode;
import com.example.investment_api.global.exception.exceptions.CustomException;

public class NotSamePasswordException extends CustomException {

    public NotSamePasswordException() {
        super(CustomErrorCode.NOT_SAME_PASSWORD);
    }
}
