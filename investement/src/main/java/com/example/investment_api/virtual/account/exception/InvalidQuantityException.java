package com.example.investment_api.virtual.account.exception;

public class InvalidQuantityException extends RuntimeException{
    public InvalidQuantityException(){super("매도 수량은 0 이상이어야 합니다.");}
}
