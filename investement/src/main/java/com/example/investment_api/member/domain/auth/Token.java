package com.example.investment_api.member.domain.auth;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface Token {

    String createToken(Long memberId);
    DecodedJWT verifyToken(String token);
}
