package com.example.investment_api.member.ui.auth.dto;

public record LoginResponse(
        String token,
        Long memberId,
        String memberName,
        String memberNickName,
        int annualIncome,
        int deposit
) {
}
