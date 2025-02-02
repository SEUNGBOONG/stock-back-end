package com.example.investment_api.member.controller.auth.dto;

public record LoginResponse(
        String token,
        Long memberId,
        String memberName,
        String memberNickName,
        String memberEmail,
        int annualIncome,
        int deposit
) {
}
