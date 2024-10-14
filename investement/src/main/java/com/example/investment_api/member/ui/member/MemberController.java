package com.example.investment_api.member.ui.member;

import com.example.investment_api.member.application.member.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    @CrossOrigin(origins = {"http://13.209.4.56:8080", "http://localhost:3000"})
    public ResponseEntity<Void> showMember() {
        return ResponseEntity.ok().build();
    }
}
