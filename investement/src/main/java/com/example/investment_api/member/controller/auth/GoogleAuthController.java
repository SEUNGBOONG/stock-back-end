package com.example.investment_api.member.controller.auth;

import com.example.investment_api.member.service.auth.GoogleAuthService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthController {

    private final GoogleAuthService service;

    @GetMapping("/google/login")
    public ResponseEntity<Void> googleLogin(HttpServletResponse response) throws IOException {
        String googleAuthUrl = service.getUrl();
        response.sendRedirect(googleAuthUrl); // 바로 리다이렉트
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }
}
