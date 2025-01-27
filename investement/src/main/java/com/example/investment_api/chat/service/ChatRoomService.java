package com.example.investment_api.chat.service;

import com.example.investment_api.chat.controller.dto.ChatMessageDTO;
import com.example.investment_api.chat.domain.StockOwnership;
import com.example.investment_api.chat.domain.entity.ChatRoom;
import com.example.investment_api.chat.domain.entity.ChatMessage;
import com.example.investment_api.chat.domain.repository.ChatRoomRepository;
import com.example.investment_api.chat.domain.repository.ChatMessageRepository;
import com.example.investment_api.chat.exception.AlreadyExistChatRoom;
import org.springframework.stereotype.Service;


@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final StockOwnership stockOwnership;

    public ChatRoomService(ChatRoomRepository chatRoomRepository,
                           ChatMessageRepository chatMessageRepository,
                           StockOwnership stockOwnership) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.stockOwnership = stockOwnership;
    }

    public ChatRoom getChatRoomByStock(String stockName) {
        return chatRoomRepository.findByStockName(stockName)
                .orElseThrow(AlreadyExistChatRoom::new);
    }

    public void checkIfCanJoin(Long memberId, String stockName) {
        stockOwnership.validateOwnership(memberId, stockName);
    }

    public ChatRoom createChatRoom(String stockName) {
        if (chatRoomRepository.findByStockName(stockName).isPresent()) {
            throw new AlreadyExistChatRoom();
        }
        ChatRoom newChatRoom = new ChatRoom(stockName);
        return chatRoomRepository.save(newChatRoom);
    }

    // 메시지 전송
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        ChatMessage message = new ChatMessage(chatRoom, chatMessageDTO.getSenderId(), chatMessageDTO.getContent());
        chatMessageRepository.save(message);

        return new ChatMessageDTO(
                chatRoom.getId(),
                message.getSenderId(),
                message.getContent(),
                message.getCreatedAt().toString()
        );
    }
}
