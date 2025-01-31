package com.example.investment_api.chat.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Long roomId;
    private String stockName;
    private Long senderId;
    private String content;

}
