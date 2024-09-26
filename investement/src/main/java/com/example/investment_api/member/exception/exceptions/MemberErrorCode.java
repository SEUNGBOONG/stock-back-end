package com.example.investment_api.member.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {

    ALREADY_EXIST_MEMBER_EMAIL(HttpStatus.CONFLICT, "A001", "유저 이메일 이미 존재합니다."),
    NOT_SAME_PASSWORD(HttpStatus.CONFLICT, "A002", "비밀번호가 일치하지 않습니다."),
    DUPLICATED_NICK_NAME(HttpStatus.CONFLICT, "A003", "닉네임이 중복됩니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "A004", "이메일이 중복됩니다.");

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }
}
