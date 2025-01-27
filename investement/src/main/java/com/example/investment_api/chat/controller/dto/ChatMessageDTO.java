package com.example.investment_api.chat.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ChatMessageDTO {
    private Long roomId;
    private Long senderId;
    private String content;
    private String createdAt;

    @JsonCreator
    public ChatMessageDTO(
            @JsonProperty("roomId") Long roomId,
            @JsonProperty("senderId") Long senderId,
            @JsonProperty("content") String content,
            @JsonProperty("createdAt") String createdAt) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
