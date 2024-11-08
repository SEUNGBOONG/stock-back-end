package com.example.investment_api.member.exception;

public class TwoHundredMillionException extends Throwable {
    public TwoHundredMillionException() {
        super("연봉은 2억까지만 포트폴리오를 구성해줍니다.");
    }
}
