package com.example.investment_api.virtual.alarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long memberId) {

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(memberId, sseEmitter);

        sseEmitter.onCompletion(() -> emitters.remove(memberId));
        sseEmitter.onTimeout(() -> emitters.remove(memberId));
        sseEmitter.onError((e) -> emitters.remove(memberId));

        try {
            sseEmitter.send(SseEmitter.event().name("connect")
                    .data("연결 성공"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sseEmitter;
    }
}
