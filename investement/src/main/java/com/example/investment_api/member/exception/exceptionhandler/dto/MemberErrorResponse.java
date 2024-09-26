package com.example.investment_api.member.exception.exceptionhandler.dto;

public record MemberErrorResponse(
        String customCode,
        String message
) {
}
