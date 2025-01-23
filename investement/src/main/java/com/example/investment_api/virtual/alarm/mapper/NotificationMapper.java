package com.example.investment_api.virtual.alarm.mapper;

import com.example.investment_api.virtual.alarm.controller.dto.NotificationDTO;
import com.example.investment_api.virtual.alarm.domain.Notification;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class NotificationMapper {

    public NotificationDTO toDTO(Notification notification) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return NotificationDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .createdAT(notification.getCreatedAt().format(formatter))
                .url(notification.getUrl())
                .build();
    }
}
