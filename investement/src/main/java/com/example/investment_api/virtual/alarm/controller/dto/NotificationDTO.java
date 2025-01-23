package com.example.investment_api.virtual.alarm.controller.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationDTO(
        Long id,
        String message,
        LocalDateTime createdAT,
        String url
) {
}
