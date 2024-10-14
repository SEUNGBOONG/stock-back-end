package com.example.investment_api.member.ui.member;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberController {

    @CrossOrigin(origins = {"http://13.209.4.56:8080", "http://localhost:3000"})
    @GetMapping("/members")
    public ResponseEntity<Void> showMember() {
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = {"http://13.209.4.56:8080", "http://localhost:3000"})
    @RequestMapping(value = "/members", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        return ResponseEntity.ok().build();
    }
}
