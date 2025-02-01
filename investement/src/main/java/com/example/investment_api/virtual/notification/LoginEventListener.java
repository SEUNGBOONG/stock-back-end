package com.example.investment_api.virtual.notification;

import com.example.investment_api.virtual.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginEventListener {

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void handleLoginEvent(LoginEvent event){
        Long memberId = event.getMemberId();
        try {
            notificationService.sendFluctuationTop3NewsNotification(memberId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
