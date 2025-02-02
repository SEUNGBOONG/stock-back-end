package com.example.investment_api.member.controller.auth;

import com.example.investment_api.member.controller.auth.dto.LoginResponse;
import com.example.investment_api.member.service.auth.GoogleAuthService;
import com.example.investment_api.member.service.auth.client.GoogleOAuthClient;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthController {

    private final GoogleAuthService service;
    private final GoogleOAuthClient client;

    @GetMapping("/google/login")
    public ResponseEntity<Void> startGoogleLogin(HttpServletResponse response) throws IOException {
        String googleAuthUrl = service.getUrl();
        response.sendRedirect(googleAuthUrl); // 바로 리다이렉트
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @GetMapping("/google/callback")
    public ResponseEntity<LoginResponse> googleLogin(@RequestParam("code") String code) {
        LoginResponse response = service.googleLogin(code);
        System.out.println(response);
        log.info("소셜 로그인");
        return ResponseEntity.ok(response);
    }
}
