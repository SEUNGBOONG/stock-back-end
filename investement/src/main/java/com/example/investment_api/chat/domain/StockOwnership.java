package com.example.investment_api.chat.domain;

import com.example.investment_api.virtual.account.domain.MemberAccountRepository;
import org.springframework.stereotype.Component;

@Component
public class StockOwnership {

    private final MemberAccountRepository memberAccountRepository;

    public StockOwnership(MemberAccountRepository memberAccountRepository) {
        this.memberAccountRepository = memberAccountRepository;
    }

    // 주식 보유 여부 확인
    public boolean canJoinChatRoom(Long memberId, String stockName) {
        return memberAccountRepository.findByMemberIdAndStockName(memberId, stockName).isPresent();
    }

    // 주식 보유 여부 확인 후 예외를 던지는 메서드
    public void validateOwnership(Long memberId, String stockName) {
        if (!canJoinChatRoom(memberId, stockName)) {
            throw new IllegalStateException("주식을 보유한 사람만 채팅방에 입장할 수 있습니다.");
        }
    }
}
