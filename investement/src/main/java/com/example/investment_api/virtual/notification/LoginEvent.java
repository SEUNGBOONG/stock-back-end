package com.example.investment_api.virtual.notification;

public class LoginEvent {

    private final Long memberId;

    public LoginEvent(Long memberId){
        this.memberId = memberId;
    }

    public Long getMemberId(){
        return memberId;
    }
}
