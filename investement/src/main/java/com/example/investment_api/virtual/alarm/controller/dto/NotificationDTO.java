package com.example.investment_api.virtual.alarm.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationDTO(
        Long id,
        String message,
        String createdAT,
        String url
) {
}
