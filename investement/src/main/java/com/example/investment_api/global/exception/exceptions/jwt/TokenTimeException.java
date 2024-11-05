package com.example.investment_api.global.exception.exceptions.jwt;

import com.example.investment_api.global.exception.exceptions.CustomErrorCode;
import com.example.investment_api.global.exception.exceptions.CustomException;

public class TokenTimeException extends CustomException {

    public TokenTimeException() {
        super(CustomErrorCode.EXPIRED_TOKEN);
    }
}
