package com.example.investment_api.chat.controller;

import com.example.investment_api.chat.domain.entity.ChatMessage;

import com.example.investment_api.chat.domain.repository.ChatMessageRepository;

import com.example.investment_api.virtual.account.domain.MemberAccountRepository;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final MemberAccountRepository memberAccountRepository;

    public WebSocketChatController(ChatMessageRepository chatMessageRepository, MemberAccountRepository memberAccountRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.memberAccountRepository = memberAccountRepository;
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable Long roomId, ChatMessage message) {
        return message;
    }

}
