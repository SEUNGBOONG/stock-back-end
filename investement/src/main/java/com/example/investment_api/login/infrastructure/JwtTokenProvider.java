package com.example.investment_api.login.infrastructure;

import com.example.investment_api.login.auth.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Getter
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-period}")
    private int expirationPeriod;

    private Key key;

    @PostConstruct
    private void createKey() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    @Override
    public String create(Long id) {
        Claims claims = Jwts.claims();
        claims.put("memberId", id);
        return createToken(claims);
    }

    private String createToken(Claims claims) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredAt(now))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Date expiredAt(Date createdAt) {
        return new Date(createdAt.getTime() + expirationPeriod);
    }

    @Override
    public Long extract(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("memberId", Long.class);
    }
}
