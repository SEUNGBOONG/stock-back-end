package com.example.investment_api.member.ui.member.dto;

public record SignUpRequest(
        String memberEmail,
        String memberName,
        String memberPassword,
        String memberNickName,
        int annualIncome
) {
}
