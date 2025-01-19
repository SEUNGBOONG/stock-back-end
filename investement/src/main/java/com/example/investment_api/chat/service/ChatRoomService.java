package com.example.investment_api.chat.service;

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
    private final ChatMessageRepository chatMessageRepository;  // 메시지 저장을 위한 repository
    private final StockOwnership stockOwnership;

    public ChatRoomService(ChatRoomRepository chatRoomRepository,
                           ChatMessageRepository chatMessageRepository,
                           StockOwnership stockOwnership) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;  // 메시지 저장을 위한 repository 초기화
        this.stockOwnership = stockOwnership;
    }

    // 1. 주식명으로 채팅방 검색
    public ChatRoom getChatRoomByStock(String stockName) {
        return chatRoomRepository.findByStockName(stockName)
                .orElseThrow(AlreadyExistChatRoom::new);
    }

    // 2. 주식 보유 여부 확인 (주식을 보유한 사람만 채팅방에 입장 가능)
    public void checkIfCanJoin(Long memberId, String stockName) {
        stockOwnership.validateOwnership(memberId, stockName);
    }

    // 채팅방 생성
    public ChatRoom createChatRoom(String stockName) {
        // 이미 존재하는 채팅방 확인
        if (chatRoomRepository.findByStockName(stockName).isPresent()) {
            throw new AlreadyExistChatRoom();
        }

        // 새로운 채팅방 생성
        ChatRoom newChatRoom = new ChatRoom(stockName);
        return chatRoomRepository.save(newChatRoom);
    }

    // 3. 채팅 메시지 저장
    public ChatMessage sendMessage(Long roomId, Long senderId, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        // 메시지 객체 생성
        ChatMessage message = new ChatMessage(chatRoom, senderId, content);

        // 메시지 저장
        return chatMessageRepository.save(message);
    }
}
