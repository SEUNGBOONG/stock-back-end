package com.example.investment_api.member.ui.auth.dto;

public record SignUpRequest(
        String memberEmail,
        String memberName,
        String memberPassword,
        String memberNickName,
        int annualIncome,
        boolean propensity //적극적 true 소극적 false
) {
}
