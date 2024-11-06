package com.example.investment_api.virtual.account.exception;

public class InsufficientStockQuantityException extends RuntimeException{
    public InsufficientStockQuantityException(){super("보유 주식 수량이 부족합니다.");}
}
