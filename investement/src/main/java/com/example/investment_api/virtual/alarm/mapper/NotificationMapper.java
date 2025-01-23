package com.example.investment_api.virtual.alarm.mapper;

import com.example.investment_api.virtual.alarm.controller.dto.NotificationDTO;
import com.example.investment_api.virtual.alarm.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .createdAT(notification.getCreatedAt())
                .url(notification.getUrl())
                .build();
    }
}
