package com.example.investment_api.virtual.alarm.controller.dto;

import java.time.LocalDateTime;

public record NotificationDTO(
        Long id,
        String message,
        LocalDateTime createdAT
) {
}
