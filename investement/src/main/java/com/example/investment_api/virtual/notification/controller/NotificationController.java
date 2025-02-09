package com.example.investment_api.virtual.notification.controller;

import com.example.investment_api.global.annotation.Member;
import com.example.investment_api.virtual.notification.controller.dto.NotificationDTO;
import com.example.investment_api.virtual.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification/subscribe")
    public SseEmitter subscribe(@Member Long memberId) {
        return notificationService.subscribe(memberId);
    }

    @GetMapping("/notifications/{memberId}")
    public List<NotificationDTO> getNotifications(@PathVariable Long memberId) {
        return notificationService.getNotificationsForMember(memberId);
    }
}
