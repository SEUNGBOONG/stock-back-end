package com.example.investment_api.member.domain.member;

import com.example.investment_api.member.exception.NotEnoughDeposit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private int deposit;

    public MemberDeposit(Long memberId, int deposit) {
        this.memberId = memberId;
        this.deposit = deposit;
    }

    public void calculateDeposit(int buyMoney, int buyCount) {
        if(deposit < buyCount* buyMoney) throw new NotEnoughDeposit();
        deposit-=buyCount*buyMoney;
    }

    public void calculateSellDeposit(int sellMoney, int buyCount){
        deposit+= sellMoney*buyCount;
    }
}
