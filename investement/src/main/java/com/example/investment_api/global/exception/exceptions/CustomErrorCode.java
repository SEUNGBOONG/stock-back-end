package com.example.investment_api.global.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrorCode {

    NOT_FIND_TOKEN(HttpStatus.NOT_FOUND, "T001", "토큰을 찾을 수 없습니다."),
    EXPIRED_TOKEN(HttpStatus.NOT_FOUND, "T002", "토큰 시간이 만료됐습니다.");

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    CustomErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }
}
