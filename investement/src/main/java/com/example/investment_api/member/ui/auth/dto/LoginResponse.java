package com.example.investment_api.member.ui.auth.dto;

public record LoginResponse(
        String token,
        String memberName,
        String memberNickName
) {
}
