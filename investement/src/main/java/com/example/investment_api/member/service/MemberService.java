package com.example.investment_api.member.service;

import com.example.investment_api.member.controller.dto.SignUpRequest;
import com.example.investment_api.member.domain.Member;
import com.example.investment_api.member.mapper.MemberMapper;
import com.example.investment_api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signUp(SignUpRequest signUpRequest) {
        Member member = MemberMapper.toMember(signUpRequest);
        checkDuplicateMemberNickName(member.getMemberNickName());
        checkDuplicateMemberEmail(member.getMemberEmail());
        memberRepository.save(member);
        return member;
    }

    private void checkDuplicateMemberNickName(String nickName) {
        if (memberRepository.existsByMemberNickName(nickName)) {
            throw new RuntimeException("닉네임 중복");
        }
    }

    private void checkDuplicateMemberEmail(String email) {
        if (memberRepository.existsByMemberEmail(email)) {
            throw new RuntimeException("이메일 중복");
        }
    }
}
