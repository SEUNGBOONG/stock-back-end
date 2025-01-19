package com.example.investment_api.chat.controller;

import com.example.investment_api.chat.domain.entity.ChatMessage;
import com.example.investment_api.chat.domain.entity.ChatRoom;
import com.example.investment_api.chat.service.ChatRoomService;

import com.example.investment_api.global.annotation.Member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    // 1. 주식명으로 채팅방 검색
    @GetMapping("/search")
    public ResponseEntity<ChatRoom> getChatRoomByStock(
            @RequestParam String stockName,
            @Member Long memberId) {

        try {
            // 2. 채팅방 존재 여부 확인 및 주식 보유 여부 확인
            ChatRoom chatRoom = chatRoomService.getChatRoomByStock(stockName);

            chatRoomService.checkIfCanJoin(memberId, stockName);

            return ResponseEntity.ok(chatRoom);  // 채팅방 반환

        } catch (IllegalArgumentException | IllegalStateException e) {
            // 예외 발생 시 400 또는 403 상태 반환
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    // 채팅방 생성
    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(
            @RequestParam String stockName,
            @Member Long memberId) {

        chatRoomService.checkIfCanJoin(memberId, stockName);

        // 채팅방 생성
        ChatRoom newChatRoom = chatRoomService.createChatRoom(stockName);
        return ResponseEntity.ok(newChatRoom);  // 생성된 채팅방 반환
    }

    // 3. 채팅 메시지 보내기
    @PostMapping("/{roomId}/messages")
    public ResponseEntity<ChatMessage> sendMessage(
            @PathVariable Long roomId,
            @RequestBody ChatMessage message) {  // JSON 데이터를 @RequestBody로 받음

        try {
            // 채팅방에서 메시지 전송 처리
            message = chatRoomService.sendMessage(roomId, message.getSenderId(), message.getContent());
            return ResponseEntity.ok(message);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
