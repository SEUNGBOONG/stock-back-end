package com.example.investment_api.virtual.notification.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;
    private Long memberId;
    private String message;
    private LocalDateTime createdAt;
    private String url;

    @Builder
    public Notification(Long memberId, String message, String url, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.message = message;
        this.url = url;
        this.createdAt = LocalDateTime.now();
    }
}
