package com.example.investment_api.member.domain.member;

import com.example.investment_api.member.exception.exceptions.auth.NotSamePasswordException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberEmail;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String memberPassword;

    @Column(nullable = false)
    private String memberNickName;

    @Column(nullable = false)
    private int annualIncome;

    @Column(nullable = false)
    private boolean propensity; //true -> 적극적투자 , false 소극적투자

    @Column(nullable = false)
    private int deposit;

    @Column(nullable = false)
    private boolean releaseCheck; //true -> 공개, false 비공개

    public Member(final String memberEmail, final String memberName, final String memberPassword, final String memberNickName, final int annualIncome, final boolean propensity, final int deposit, final boolean releaseCheck) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.memberPassword = memberPassword;
        this.memberNickName = memberNickName;
        this.annualIncome = annualIncome;
        this.propensity = propensity;
        this.deposit = deposit;
        this.releaseCheck = releaseCheck;
    }

    public void checkPassword(String requestPassword) {
        if (!Objects.equals(memberPassword, requestPassword)) {
            throw new NotSamePasswordException();
        }
    }

    public void calculateDeposit(int buyMoney, int buyCount) {
        deposit-=buyCount*buyMoney;
    }

    public void calculateSellDeposit(int sellMoney, int buyCount){
        deposit+= sellMoney*buyCount;
    }
}
