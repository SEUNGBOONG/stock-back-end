package com.example.investment_api.member.application.member;

import com.example.investment_api.member.ui.member.dto.SignUpRequest;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    public Member signUp(SignUpRequest signUpRequest) {
        Member member = MemberMapper.toMember(signUpRequest);
        checkDuplicateMemberNickName(member.getMemberNickName());
        checkDuplicateMemberEmail(member.getMemberEmail());
        memberJpaRepository.save(member);
        return member;
    }

    private void checkDuplicateMemberNickName(String nickName) {
        if (memberJpaRepository.existsByMemberNickName(nickName)) {
            throw new RuntimeException("닉네임 중복");
        }
    }

    private void checkDuplicateMemberEmail(String email) {
        if (memberJpaRepository.existsByMemberEmail(email)) {
            throw new RuntimeException("이메일 중복");
        }
    }
}
