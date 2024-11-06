package com.example.investment_api.member.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode {

    NOT_SAME_PASSWORD(HttpStatus.CONFLICT, "A001", "비밀번호가 일치하지 않습니다."),
    DUPLICATED_NICK_NAME(HttpStatus.CONFLICT, "A002", "닉네임이 중복됩니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "A003", "이메일이 중복됩니다."),
    NOT_FOUND_MEMBER_BY_EMAIL(HttpStatus.NOT_FOUND, "A004","유저를 찾을 수 없습니다"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "A005", "잘못된 이메일 형식입니다."),
    INVALID_SIGNUP_REQUEST(HttpStatus.BAD_REQUEST, "A006", "모든 정보를 입력해주세요."),
    INVALID_LOGIN_REQUEST(HttpStatus.BAD_REQUEST, "A007", "잘못된 로그인 입력입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "A008", "비밀번호는 8자리 이상이어야 합니다."),
    NOT_FOUND_MEMBER_DEPOSIT(HttpStatus.NOT_FOUND, "A009","계좌를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String customCode;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String customCode, String message) {
        this.httpStatus = httpStatus;
        this.customCode = customCode;
        this.message = message;
    }
}
