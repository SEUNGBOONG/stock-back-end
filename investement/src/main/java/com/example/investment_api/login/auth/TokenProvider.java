package com.example.investment_api.login.auth;

public interface TokenProvider {

    String create(final Long id);

    Long extract(final String token);
}
