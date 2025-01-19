package com.example.investment_api.chat.service;

import com.example.investment_api.chat.domain.StockOwnership;
import com.example.investment_api.chat.domain.entity.ChatRoom;

import com.example.investment_api.chat.domain.repository.ChatRoomRepository;

import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.domain.MemberAccountRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final StockOwnership stockOwnership;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, StockOwnership stockOwnership) {
        this.chatRoomRepository = chatRoomRepository;
        this.stockOwnership = stockOwnership;
    }

    // 1. 주식명으로 채팅방 검색
    public ChatRoom getChatRoomByStock(String stockName) {
        return chatRoomRepository.findByStockName(stockName)
                .orElseThrow(() -> new IllegalArgumentException("해당 주식명으로 채팅방이 존재하지 않습니다."));
    }

    // 2. 주식 보유 여부 확인 (주식을 보유한 사람만 채팅방에 입장 가능)
    public void checkIfCanJoin(Long memberId, String stockName) {
        stockOwnership.validateOwnership(memberId, stockName);
    }

    public ChatRoom createChatRoom(String stockName) {
        // 이미 존재하는 채팅방 확인
        if (chatRoomRepository.findByStockName(stockName).isPresent()) {
            throw new IllegalArgumentException("해당 주식명으로 이미 채팅방이 존재합니다.");
        }

        // 새로운 채팅방 생성
        ChatRoom newChatRoom = new ChatRoom(stockName);
        return chatRoomRepository.save(newChatRoom);
    }
}
