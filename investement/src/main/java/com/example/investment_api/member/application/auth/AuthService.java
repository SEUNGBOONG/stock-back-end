package com.example.investment_api.member.application.auth;

import com.example.investment_api.member.ui.auth.dto.LoginRequest;
import com.example.investment_api.member.infrastructure.auth.JwtTokenProvider;
import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public String login(LoginRequest loginRequest) {
        Member member = findMemberByEmail(loginRequest.memberEmail());
        member.checkPassword(loginRequest.memberPassword());
        return jwtTokenProvider.create(member.getId());
    }

    private Member findMemberByEmail(String email) {
        return memberJpaRepository.findMemberByMemberEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }
}
