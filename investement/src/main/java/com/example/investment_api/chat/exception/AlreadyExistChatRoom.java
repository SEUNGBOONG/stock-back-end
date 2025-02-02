package com.example.investment_api.chat.exception;

public class AlreadyExistChatRoom extends IllegalArgumentException {
    public AlreadyExistChatRoom() {
        super("이미 채팅방이 존재합니다.");
    }
}
