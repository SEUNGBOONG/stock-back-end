package com.example.investment_api.login.controller.dto;

public record LoginRequest(
        String memberEmail,
        String memberPassword
) {
}
