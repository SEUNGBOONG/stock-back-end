package com.example.investment_api.virtual.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {

    Optional<List<MemberAccount>> findByMemberId(Long memberId);

    Optional<MemberAccount> findByMemberIdAndStockName(Long memberId, String stockName);

}
