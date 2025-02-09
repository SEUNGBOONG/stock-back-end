package com.example.investment_api.virtual.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishLoginEvent(Long memberId){
        eventPublisher.publishEvent(new LoginEvent(memberId));
    }
}
