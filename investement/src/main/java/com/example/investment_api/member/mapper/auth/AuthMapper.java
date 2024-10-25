package com.example.investment_api.member.mapper.auth;

import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.ui.auth.dto.SignUpRequest;
import com.example.investment_api.member.ui.auth.dto.SignUpResponse;

public class AuthMapper {

    public static Member toMember(SignUpRequest signUpRequest) {
        return new Member(
                signUpRequest.memberEmail(),
                signUpRequest.memberName(),
                signUpRequest.memberPassword(),
                signUpRequest.memberNickName(),
                signUpRequest.annualIncome());
    }

    public static SignUpResponse toSignUpResponse(Member member) {
        return new SignUpResponse(member.getId(), member.getMemberName(), member.getMemberEmail(),
                member.getMemberPassword(), member.getMemberNickName(), member.getAnnualIncome());
    }
}
