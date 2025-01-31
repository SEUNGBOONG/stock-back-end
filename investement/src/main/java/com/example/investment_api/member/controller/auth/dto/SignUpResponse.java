package com.example.investment_api.member.controller.auth.dto;

public record SignUpResponse(
        Long id,
        String memberName,
        String memberEmail,
        String memberPassword,
        String memberNickname,
        int memberAnnualIncome,
        boolean memberPropensity,
        boolean releaseCheck) {
}
