package com.example.investment_api.member.infrastructure.member;

import com.example.investment_api.member.domain.member.MemberDeposit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDepositJpaRepository extends JpaRepository<MemberDeposit, Long> {

    Optional<MemberDeposit> findByMemberId(Long memberId);
}
