package com.example.investment_api.virtual.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
    List<StockOrder> findByMemberId(Long memberId);
    List<StockOrder> findByIsProcessedFalse();
}
