package com.example.investment_api.chat.controller;

import com.example.investment_api.chat.controller.dto.ChatMessageDTO;
import com.example.investment_api.chat.domain.entity.ChatRoom;
import com.example.investment_api.chat.service.ChatRoomService;
import com.example.investment_api.global.annotation.Member;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/search")
    public ResponseEntity<ChatRoom> getChatRoomByStock(
            @RequestParam String stockName,
            @Member Long memberId) {

        try {
            ChatRoom chatRoom = chatRoomService.getChatRoomByStock(stockName);
            chatRoomService.checkIfCanJoin(memberId, stockName);
            return ResponseEntity.ok(chatRoom);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestParam String stockName, @Member Long memberId) {

        chatRoomService.checkIfCanJoin(memberId, stockName);
        ChatRoom newChatRoom = chatRoomService.createChatRoom(stockName);
        return ResponseEntity.ok(newChatRoom);
    }

    @PostMapping("/{roomId}/messages")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @PathVariable Long roomId,
            @Member Long memberId,
            @RequestBody @Valid ChatMessageDTO messageDTO) {
        try {
            ChatMessageDTO savedMessage = chatRoomService.sendMessage(memberId, messageDTO.getStockName(), messageDTO.getContent());
            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getAllMessagesInRoom(@PathVariable Long roomId) {
        try {
            List<ChatMessageDTO> messages = chatRoomService.getMessages(roomId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
