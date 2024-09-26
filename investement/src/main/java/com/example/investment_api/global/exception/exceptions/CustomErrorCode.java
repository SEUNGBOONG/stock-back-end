package com.example.investment_api.global.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrorCode {

    ALREADY_EXIST_MEMBER_EMAIL(HttpStatus.BAD_REQUEST, "A001", "유저 이메일 이미 존재합니다."),
    NOT_SAME_PASSWORD(HttpStatus.BAD_REQUEST, "A002", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    CustomErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }
}
