package com.example.investment_api.global.exception.exceptions.jwt;

import com.example.investment_api.global.exception.exceptions.CustomErrorCode;
import com.example.investment_api.global.exception.exceptions.CustomException;

public class NotFoundTokenException extends CustomException {

    public NotFoundTokenException() {
        super(CustomErrorCode.NOT_FIND_TOKEN);
    }
}
