package com.example.investment_api.global.exception.exceptionhandler.dto;

public record ErrorResponse(
        String customCode,
        String message
) {
}
