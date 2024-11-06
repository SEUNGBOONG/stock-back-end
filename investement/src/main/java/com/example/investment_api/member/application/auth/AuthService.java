package com.example.investment_api.member.application.auth;

import com.example.investment_api.member.domain.member.MemberDeposit;
import com.example.investment_api.member.exception.exceptions.auth.DuplicateEmailException;
import com.example.investment_api.member.exception.exceptions.auth.DuplicateNickNameException;
import com.example.investment_api.member.exception.exceptions.auth.NotFoundMemberByEmailException;

import com.example.investment_api.member.exception.exceptions.member.NotFoundMemberDepositException;
import com.example.investment_api.member.infrastructure.member.MemberDepositJpaRepository;
import com.example.investment_api.member.mapper.auth.AuthMapper;
import com.example.investment_api.member.ui.auth.dto.LoginRequest;

import com.example.investment_api.member.infrastructure.auth.JwtTokenProvider;
import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.member.ui.auth.dto.LoginResponse;
import com.example.investment_api.member.ui.auth.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberDepositJpaRepository memberDepositJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Member signUp(SignUpRequest signUpRequest) {
        Member member = AuthMapper.toMember(signUpRequest);
        checkDuplicateMemberNickName(member.getMemberNickName());
        checkDuplicateMemberEmail(member.getMemberEmail());
        Member savedMember = memberJpaRepository.save(member);
        MemberDeposit deposit = new MemberDeposit(savedMember.getId(), 100000000);
        memberDepositJpaRepository.save(deposit);

        return savedMember;
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
    public LoginResponse login(LoginRequest loginRequest) {
        Member member = findMemberByEmail(loginRequest.memberEmail());
        member.checkPassword(loginRequest.memberPassword());
        String token = jwtTokenProvider.createToken(member.getId());

        return new LoginResponse(token, member.getMemberName(), member.getMemberNickName());
    }

    private Member findMemberByEmail(String email) {
        return memberJpaRepository.findMemberByMemberEmail(email)
                .orElseThrow(NotFoundMemberByEmailException::new);
    }

    private MemberDeposit findMemberDepositByMemberId(Long memberId) {
        return memberDepositJpaRepository.findByMemberId(memberId)
                .orElseThrow(NotFoundMemberDepositException::new);
    }
}
