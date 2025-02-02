package com.example.investment_api.chat.controller;

import com.example.investment_api.chat.controller.dto.ChatMessageDTO;
import com.example.investment_api.chat.service.ChatRoomService;

import com.example.investment_api.global.annotation.Member;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    private final ChatRoomService chatRoomService;

    public WebSocketChatController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    // WebSocket을 통해 메시지 전송
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageDTO sendMessage(@DestinationVariable Long roomId, @Member Long memberId, ChatMessageDTO message) {
        // ChatMessageDTO를 보내고 반환값을 DTO로 변환하여 리턴
        return chatRoomService.sendMessage(memberId, message.getStockName(), message.getContent());
    }
}
