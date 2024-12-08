package com.example.investment_api.virtual.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberOrderRepository extends JpaRepository<MemberOrder, Long> {
    Optional<List<MemberOrder>> findMemberOrdersByMemberIdAndStockName(Long memberId, String stockName);
}
