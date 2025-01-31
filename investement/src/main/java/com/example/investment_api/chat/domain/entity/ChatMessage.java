package com.example.investment_api.chat.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private Long senderId;

    private String content;

    private LocalDateTime createdAt; // 메시지 생성 시간

    // 생성자, Getter, Setter 등
    public ChatMessage(ChatRoom chatRoom, Long senderId, String content) {
        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = LocalDateTime.now();  // 메시지 전송 시간 설정
    }

    public ChatMessage() {

    }

    // 기타 생성자, Getter, Setter
}
