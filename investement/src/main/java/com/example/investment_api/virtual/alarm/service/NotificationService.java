package com.example.investment_api.virtual.alarm.service;

import com.example.investment_api.virtual.alarm.controller.dto.NotificationDTO;
import com.example.investment_api.virtual.alarm.domain.Notification;
import com.example.investment_api.virtual.alarm.domain.NotificationType;
import com.example.investment_api.virtual.alarm.mapper.NotificationMapper;
import com.example.investment_api.virtual.alarm.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
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

    public void sendOrderNotification(Long memberId, NotificationType type, String stockName, int quantity) {
        String message = generateNotificationMessage(type, stockName, quantity);
        String url = "https://growfolio-nu.vercel.app/login";

        Notification notification = createNotification(memberId, message, url);
        notificationRepository.save(notification);

        SseEmitter emitter = emitters.get(memberId);
        if (emitter != null) {
            NotificationDTO notificationDTO = notificationMapper.toDTO(notification);
            sendToClient(emitter, memberId.toString(), notificationDTO);
        }
    }

    private Notification createNotification(Long memberId, String message, String url) {
        return Notification.builder()
                .memberId(memberId)
                .message(message)
                .url(url)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("order-notification")
                    .data(data));
        } catch (IOException exception) {
            emitters.remove(Long.valueOf(id));
            throw new RuntimeException("알림 전송 실패!", exception);
        }
    }


    private String generateNotificationMessage(NotificationType type, String stockName, int quantity) {
        switch (type) {
            case BUY_SUCCESS:
                return "매수 성공: " + stockName + " " + quantity + "주가 체결되었습니다.";
            case SELL_SUCCESS:
                return "매도 성공: " + stockName + " " + quantity + "주가 체결되었습니다.";
            default:
                return "알림: 주문 상태가 업데이트되었습니다.";
        }
    }

    public List<NotificationDTO> getNotificationsForMember(Long memberId) {
        List<Notification> notifications = notificationRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        return notifications.stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
