package com.example.investment_api.member.ui.auth.dto;

public record LoginRequest(
        String memberEmail,
        String memberPassword
) {
}
