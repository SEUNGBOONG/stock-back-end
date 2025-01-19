package com.example.investment_api.chat.exception;

public class NotFoundChattingRoom extends IllegalArgumentException {
    public NotFoundChattingRoom() {
        super("채팅방을 찾을 수 없습니다.");
    }
}
