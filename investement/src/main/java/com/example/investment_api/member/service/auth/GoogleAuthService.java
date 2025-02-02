package com.example.investment_api.member.service.auth;

import com.example.investment_api.member.controller.auth.dto.GoogleUserInfoResponse;
import com.example.investment_api.member.controller.auth.dto.LoginResponse;
import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.infrastructure.auth.JwtTokenProvider;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.member.mapper.auth.AuthMapper;
import com.example.investment_api.member.service.auth.client.GoogleOAuthClient;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleOAuthClient client;
    private final MemberJpaRepository memberJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String getUrl() {
        return client.getUrl();
    }

    public LoginResponse googleLogin(String code) {
        GoogleUserInfoResponse response = getUserInfo(code);
        Optional<Member> member = findMember(response.email());

        if(member.isEmpty()) {
            return AuthMapper.fromOAuthUser(response);
        }
        return getLoginResponse(member);
    }

    private LoginResponse getLoginResponse(Optional<Member> member) {
        Member existMember = member.get();
        String token = jwtTokenProvider.createToken(existMember.getId());

        return AuthMapper.toLoginResponse(token, existMember);
    }

    private Optional<Member> findMember(String email) {
        return memberJpaRepository.findMemberByMemberEmail(email);
    }

    private GoogleUserInfoResponse getUserInfo(String code) {
        return client.getUserInfo(code);
    }
}
