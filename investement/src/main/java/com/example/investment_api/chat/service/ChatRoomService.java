package com.example.investment_api.chat.service;

import com.example.investment_api.chat.controller.dto.ChatMessageDTO;
import com.example.investment_api.chat.domain.StockOwnership;
import com.example.investment_api.chat.domain.entity.ChatRoom;
import com.example.investment_api.chat.domain.entity.ChatMessage;
import com.example.investment_api.chat.domain.repository.ChatRoomRepository;
import com.example.investment_api.chat.domain.repository.ChatMessageRepository;
import com.example.investment_api.chat.exception.AlreadyExistChatRoom;

import com.example.investment_api.virtual.account.domain.MemberAccountRepository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final StockOwnership stockOwnership;
    private final MemberAccountRepository memberAccountRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public ChatRoomService(ChatRoomRepository chatRoomRepository,
                           ChatMessageRepository chatMessageRepository,
                           StockOwnership stockOwnership,
                           MemberAccountRepository memberAccountRepository,
                           RedisTemplate<String, Object> redisTemplate) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.stockOwnership = stockOwnership;
        this.memberAccountRepository = memberAccountRepository;
        this.redisTemplate = redisTemplate;
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
        ChatRoom chatRoom = chatRoomRepository.findByStockName(stockName)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        // 메시지 생성 및 저장
        ChatMessage message = new ChatMessage(chatRoom, chatRoom.getId(), content);
        ChatMessage savedMessage = chatMessageRepository.save(message);

        // Redis에 저장 - 최근 100개 메시지만 저장
        saveMessageToRedis(chatRoom.getId(), savedMessage);

        // DTO로 변환 후 반환
        return new ChatMessageDTO(
                savedMessage.getId(),
                stockName,
                savedMessage.getSenderId(),
                savedMessage.getContent()
        );
    }

    // Redis에서 메시지 조회

    public List<ChatMessageDTO> getMessagesFromRedis(Long chatRoomId) {
        List<Object> redisMessages = redisTemplate.opsForList().range("chatRoom:" + chatRoomId + ":messages", 0, -1);
        assert redisMessages != null;
        return redisMessages.stream()
                .map(obj -> (ChatMessage) obj)
                .map(message -> new ChatMessageDTO(
                        message.getId(),
                        message.getChatRoom().getStockName(),
                        message.getSenderId(),
                        message.getContent()
                ))
                .collect(Collectors.toList());
    }
    // DB에서 메시지 조회 후 Redis에 저장

    public List<ChatMessageDTO> getMessagesFromDB(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        List<ChatMessage> messages = chatMessageRepository.findAllByChatRoom(chatRoom);

        // DB에서 가져온 메시지를 Redis에 저장
        messages.forEach(message -> redisTemplate.opsForList().leftPush("chatRoom:" + chatRoomId + ":messages", message));

        return messages.stream()
                .map(message -> new ChatMessageDTO(
                        message.getId(),
                        chatRoom.getStockName(),
                        message.getSenderId(),
                        message.getContent()
                ))
                .collect(Collectors.toList());
    }
    // Redis에서 메시지를 먼저 조회하고, 없으면 DB에서 조회하여 반환

    public List<ChatMessageDTO> getMessages(Long chatRoomId) {
        List<ChatMessageDTO> messagesFromRedis = getMessagesFromRedis(chatRoomId);
        if (messagesFromRedis != null) {
            return messagesFromRedis;
        }
        return getMessagesFromDB(chatRoomId);
    }

    // Redis에 메시지 저장 (최근 100개 메시지만)
    private void saveMessageToRedis(Long chatRoomId, ChatMessage savedMessage) {
        String redisKey = "chatRoom:" + chatRoomId + ":messages";
        redisTemplate.opsForList().leftPush(redisKey, savedMessage);

        // Redis에서 저장된 메시지 개수가 100개를 초과하면 가장 오래된 메시지 삭제
        if (redisTemplate.opsForList().size(redisKey) > 100) {
            redisTemplate.opsForList().rightPop(redisKey);
        }
    }

}
