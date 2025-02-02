package com.example.investment_api.member.mapper.auth;

import com.example.investment_api.member.controller.auth.dto.GoogleUserInfoResponse;
import com.example.investment_api.member.controller.auth.dto.LoginResponse;
import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.controller.auth.dto.SignUpRequest;
import com.example.investment_api.member.controller.auth.dto.SignUpResponse;

public class AuthMapper {

    private static final String NOT_SERVICE_MEMBER = "회원 가입이 필요한 유저입니다.";
    private static final Long NOT_SERVICE_MEMBER_ID = Long.MIN_VALUE;
    private static final int NO_NUMBER_INFORMATION = Integer.MIN_VALUE;

    public static Member toMember(SignUpRequest signUpRequest) {
        return new Member(
                signUpRequest.memberEmail(),
                signUpRequest.memberName(),
                signUpRequest.memberPassword(),
                signUpRequest.memberNickName(),
                signUpRequest.annualIncome(),
                signUpRequest.propensity(),
                100000000,
                signUpRequest.releaseCheck()
        );
    }

    public static SignUpResponse toSignUpResponse(Member member) {
        return new SignUpResponse(member.getId(), member.getMemberName(), member.getMemberEmail(),
                member.getMemberPassword(), member.getMemberNickName(), member.getAnnualIncome(), member.isPropensity(), member.isReleaseCheck());
    }

    public static LoginResponse fromOAuthUser(GoogleUserInfoResponse userInfo) {
        return new LoginResponse(
                NOT_SERVICE_MEMBER,
                NOT_SERVICE_MEMBER_ID,
                userInfo.name(),
                NOT_SERVICE_MEMBER,
                userInfo.email(),
                NO_NUMBER_INFORMATION,
                NO_NUMBER_INFORMATION
        );
    }

    public static LoginResponse toLoginResponse(String token, Member member) {
        return new LoginResponse(
                token,
                member.getId(),
                member.getMemberName(),
                member.getMemberNickName(),
                member.getMemberEmail(),
                member.getAnnualIncome(),
                member.getDeposit()
        );
    }
}
