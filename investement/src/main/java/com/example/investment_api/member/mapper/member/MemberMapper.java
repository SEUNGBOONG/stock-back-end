package com.example.investment_api.member.mapper.member;

import com.example.investment_api.member.ui.member.dto.SignUpRequest;
import com.example.investment_api.member.ui.member.dto.SignUpResponse;

import com.example.investment_api.member.domain.member.Member;

public class MemberMapper {

    public static Member toMember(SignUpRequest signUpRequest) {
        return new Member(
                signUpRequest.memberEmail(),
                signUpRequest.memberName(),
                signUpRequest.memberPassword(),
                signUpRequest.memberNickName(),
                signUpRequest.annualIncome());
    }

    public static SignUpResponse toSignUpResponse(Member member) {
        return new SignUpResponse(member.getId(), member.getMemberNickName());
    }
}
