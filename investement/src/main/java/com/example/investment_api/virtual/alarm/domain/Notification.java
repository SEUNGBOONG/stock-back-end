package com.example.investment_api.virtual.alarm.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;
    private Long memberId;
    private String message;
    private LocalDateTime createdAt;

    public Notification(Long memberId, String message){
        this.memberId = memberId;
        this.message= message;
        this.createdAt = LocalDateTime.now();
    }
}
