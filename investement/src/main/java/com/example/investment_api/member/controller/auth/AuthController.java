package com.example.investment_api.member.controller.auth;

import com.example.investment_api.member.controller.auth.dto.LoginRequest;
import com.example.investment_api.member.controller.auth.dto.LoginResponse;
import com.example.investment_api.member.controller.auth.dto.SignUpRequest;
import com.example.investment_api.member.controller.auth.dto.SignUpResponse;
import com.example.investment_api.member.mapper.auth.AuthMapper;

import com.example.investment_api.member.service.auth.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/members")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = AuthMapper.toSignUpResponse(authService.signUp(signUpRequest));
        URI location = URI.create("/members/" + signUpResponse.id());
        log.info("유저 생성 - {}번 유저 : {}", signUpResponse.id(), signUpResponse.memberNickname());
        return ResponseEntity.created(location).body(signUpResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        log.info("로그인 성공");
        return ResponseEntity.ok(loginResponse);
    }
}
