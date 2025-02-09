package com.example.investment_api.virtual.notification.controller.dto;

import lombok.Builder;

@Builder
public record NotificationDTO(
        Long id,
        String message,
        String createdAT,
        String url
) {
}
