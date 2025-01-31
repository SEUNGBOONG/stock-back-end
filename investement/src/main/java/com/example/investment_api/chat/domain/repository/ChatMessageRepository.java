package com.example.investment_api.chat.domain.repository;

import com.example.investment_api.chat.domain.entity.ChatMessage;
import com.example.investment_api.chat.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomId(Long chatRoomId);
    List<ChatMessage> findAllByChatRoom(ChatRoom chatRoom);
}
