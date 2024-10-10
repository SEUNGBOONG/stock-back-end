package com.example.investment_api.member.application.auth;

import com.example.investment_api.member.exception.exceptions.auth.DuplicateEmailException;
import com.example.investment_api.member.exception.exceptions.auth.DuplicateNickNameException;
import com.example.investment_api.member.exception.exceptions.auth.NotFoundMemberByEmailException;

import com.example.investment_api.member.mapper.auth.AuthMapper;

import com.example.investment_api.member.ui.auth.dto.LoginRequest;

import com.example.investment_api.member.infrastructure.auth.JwtTokenProvider;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;

import com.example.investment_api.member.domain.member.Member;

import com.example.investment_api.member.ui.auth.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Member signUp(SignUpRequest signUpRequest) {
        Member member = AuthMapper.toMember(signUpRequest);
        checkDuplicateMemberNickName(member.getMemberNickName());
        checkDuplicateMemberEmail(member.getMemberEmail());
        memberJpaRepository.save(member);
        return member;
    }

    private void checkDuplicateMemberNickName(String nickName) {
        if (memberJpaRepository.existsByMemberNickName(nickName)) {
            throw new DuplicateNickNameException();
        }
    }

    private void checkDuplicateMemberEmail(String email) {
        if (memberJpaRepository.existsByMemberEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest loginRequest) {
        Member member = findMemberByEmail(loginRequest.memberEmail());
        member.checkPassword(loginRequest.memberPassword());
        return jwtTokenProvider.create(member.getId());
    }

    private Member findMemberByEmail(String email) {
        return memberJpaRepository.findMemberByMemberEmail(email)
                .orElseThrow(NotFoundMemberByEmailException::new);
    }
}
