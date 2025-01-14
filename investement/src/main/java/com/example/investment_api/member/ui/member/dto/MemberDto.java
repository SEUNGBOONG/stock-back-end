package com.example.investment_api.member.ui.member.dto;

import lombok.Getter;

@Getter
public class MemberDto {
    private String memberNickName;
    private int deposit;

    public MemberDto(String memberNickName, int deposit) {
        this.memberNickName = memberNickName;
        this.deposit = deposit;
    }

}
