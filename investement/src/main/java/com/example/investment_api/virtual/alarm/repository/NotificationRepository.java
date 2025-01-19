package com.example.investment_api.virtual.alarm.repository;

import com.example.investment_api.virtual.alarm.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
