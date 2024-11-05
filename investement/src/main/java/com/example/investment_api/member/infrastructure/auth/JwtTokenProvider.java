package com.example.investment_api.member.infrastructure.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.investment_api.member.domain.auth.Token;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements Token {

    private final Algorithm algorithm;
    private final long expirationPeriod;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration-period}") long expirationPeriod) {
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.expirationPeriod = expirationPeriod;
    }

    @Override
    public String createToken(Long memberId) {
        return JWT.create()
                .withClaim("memberId", memberId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationPeriod * 1000L))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    @Override
    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        return verifier.verify(token);
    }
}
