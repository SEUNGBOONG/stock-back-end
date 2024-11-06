package com.example.investment_api.virtual.account.exception;

public class InvalidOrderException extends RuntimeException{
    public InvalidOrderException(){super("가격과 수량은 0 이상이어야 합니다.");}
}
