package com.example.investment_api.member.application.member;

import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.member.ui.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    public String getMemberNickName(Long memberId){
        Member member= memberJpaRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        return member.getMemberNickName();
    }

    public int getMemberDeposit(Long memberId){
        Member member= memberJpaRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        return member.getDeposit();
    }

    public List<MemberDto> getTop5MembersByDeposit() {
        List<Member> members = memberJpaRepository.findTop5ByReleaseCheckTrueOrderByDepositDesc();
        return members.stream()
                .map(member -> new MemberDto(member.getMemberNickName(), member.getDeposit()))
                .toList();
    }

}
