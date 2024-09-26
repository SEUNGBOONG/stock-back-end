package com.example.investment_api.login.service;

import com.example.investment_api.login.controller.dto.LoginRequest;
import com.example.investment_api.login.infrastructure.JwtTokenProvider;
import com.example.investment_api.member.domain.Member;
import com.example.investment_api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public String login(LoginRequest loginRequest) {
        Member member = findMemberByEmail(loginRequest.memberEmail());
        member.checkPassword(loginRequest.memberPassword());
        return jwtTokenProvider.create(member.getId());
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findMemberByMemberEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }
}
