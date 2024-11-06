package com.example.investment_api.search.detail.stock.exception;

public class NoSuchStockNameException extends RuntimeException{
    public NoSuchStockNameException(){
        super("주식 정보가 존재하지 않습니다.");
    }
}
