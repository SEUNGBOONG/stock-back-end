package com.example.investment_api.chat.exception;

public class HaveNotStockException extends IllegalArgumentException {
    public HaveNotStockException() {
        super("주식을 보유한 사람만 채팅방에 입장할 수 있습니다.");
    }
}
