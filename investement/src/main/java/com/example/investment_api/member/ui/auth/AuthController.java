package com.example.investment_api.member.ui.auth;

import com.example.investment_api.member.ui.auth.dto.LoginRequest;
import com.example.investment_api.member.ui.auth.dto.LoginResponse;
import com.example.investment_api.member.application.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse(authService.login(loginRequest));
        log.info("로그인 성공");
        return ResponseEntity.ok(loginResponse);
    }
}
