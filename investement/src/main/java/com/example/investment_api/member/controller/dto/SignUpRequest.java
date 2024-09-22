package com.example.investment_api.member.controller.dto;

public record SignUpRequest(
        String memberEmail,
        String memberName,
        String memberPassword,
        String memberNickName,
        int annualIncome
) {
}
