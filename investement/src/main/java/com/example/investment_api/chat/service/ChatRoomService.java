package com.example.investment_api.chat.service;

import com.example.investment_api.chat.controller.dto.ChatMessageDTO;
import com.example.investment_api.chat.domain.StockOwnership;
import com.example.investment_api.chat.domain.entity.ChatRoom;
import com.example.investment_api.chat.domain.entity.ChatMessage;
import com.example.investment_api.chat.domain.repository.ChatRoomRepository;
import com.example.investment_api.chat.domain.repository.ChatMessageRepository;
import com.example.investment_api.chat.exception.AlreadyExistChatRoom;
import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.domain.MemberAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final StockOwnership stockOwnership;
    private final MemberAccountRepository memberAccountRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository,
                           ChatMessageRepository chatMessageRepository,
                           StockOwnership stockOwnership, final MemberAccountRepository memberAccountRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.stockOwnership = stockOwnership;
        this.memberAccountRepository = memberAccountRepository;
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
    public ChatMessageDTO sendMessage(Long memberId, String stockName, String content) {
        // 채팅방이 존재하는지 확인
        ChatRoom chatRoom = chatRoomRepository.findByStockName(stockName).
                orElseThrow(() -> new RuntimeException("ChatRoom not found"));
        Optional<MemberAccount> memberAccountOpt = memberAccountRepository.findByMemberIdAndStockName(memberId, stockName);

        // 메시지 생성 및 저장
        ChatMessage message = new ChatMessage(chatRoom, chatRoom.getId(), content);
        ChatMessage savedMessage = chatMessageRepository.save(message);

        // DTO로 변환 후 반환
        return new ChatMessageDTO(
                savedMessage.getId(),
                stockName,
                savedMessage.getSenderId(),
                savedMessage.getContent()
        );
    }

    public List<ChatMessageDTO> getAllMessagesInRoom(Long chatRoomId) {
        // 채팅방 ID로 해당 채팅방을 조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        // 채팅방에 속한 모든 메시지 조회
        List<ChatMessage> messages = chatMessageRepository.findAllByChatRoom(chatRoom);

        // 메시지들을 DTO로 변환하여 반환
        return messages.stream()
                .map(message -> new ChatMessageDTO(
                        message.getId(),
                        chatRoom.getStockName(),
                        message.getSenderId(),
                        message.getContent()
                ))
                .collect(Collectors.toList());
    }

}
