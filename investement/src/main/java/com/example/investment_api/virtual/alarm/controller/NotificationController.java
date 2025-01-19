package com.example.investment_api.virtual.alarm.controller;

import com.example.investment_api.global.annotation.Member;
import com.example.investment_api.virtual.alarm.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notification/subscribe")
    public SseEmitter subscribe(@Member Long memberId) {
        return notificationService.subscribe(memberId);
    }
}
