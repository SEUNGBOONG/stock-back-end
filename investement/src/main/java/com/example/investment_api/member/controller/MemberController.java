package com.example.investment_api.member.controller;

import com.example.investment_api.member.controller.dto.SignUpRequest;
import com.example.investment_api.member.controller.dto.SignUpResponse;
import com.example.investment_api.member.mapper.MemberMapper;
import com.example.investment_api.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<Void> createMember(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = MemberMapper.toSignUpResponse(memberService.signUp(signUpRequest));
        URI location = URI.create("/members/" + signUpResponse.id());
        log.info("유저 생성 - {}번 유저 : {}", signUpResponse.id(), signUpResponse.memberNickname());
        return ResponseEntity.created(location)
                .build();
    }
}
