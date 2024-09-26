package com.example.investment_api.login.controller;

import com.example.investment_api.login.controller.dto.LoginRequest;
import com.example.investment_api.login.controller.dto.LoginResponse;
import com.example.investment_api.login.service.AuthService;
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
