package com.example.investment_api.member.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {

    NOT_SAME_PASSWORD(HttpStatus.CONFLICT, "A001", "비밀번호가 일치하지 않습니다."),
    DUPLICATED_NICK_NAME(HttpStatus.CONFLICT, "A002", "닉네임이 중복됩니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "A003", "이메일이 중복됩니다."),
    NOT_FOUND_MEMBER_BY_EMAIL(HttpStatus.NOT_FOUND, "A004","유저를 찾을 수 없습니다"),

    NOT_FOUND_MEMBER_DEPOSIT(HttpStatus.NOT_FOUND, "D001","계좌를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }
}
