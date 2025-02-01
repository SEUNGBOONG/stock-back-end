package com.example.investment_api.member.service.auth;

import com.example.investment_api.member.service.auth.client.GoogleOAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleOAuthClient client;

    public String getUrl() {
        return client.getUrl();
    }
}
