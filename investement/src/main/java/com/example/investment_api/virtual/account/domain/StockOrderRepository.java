package com.example.investment_api.virtual.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
    Optional<List<StockOrder>> findByMemberId(Long memberId);
    Optional<List<StockOrder>> findByMemberIdAndStockName(Long memberId, String stockName);
    Optional<StockOrder> findByMemberIdAndId(Long memberId, Long orderId);
    List<StockOrder> findByIsProcessedFalse();
}
