package com.example.investment_api.member.application.member;

import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    public String getMemberNickName(Long memberId){
        Member member= memberJpaRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        return member.getMemberNickName();
    }
}
