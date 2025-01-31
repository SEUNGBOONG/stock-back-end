package com.example.investment_api.member.controller.auth.dto;

public record LoginRequest(
        String memberEmail,
        String memberPassword
) {
}
