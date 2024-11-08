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

    @Column
    private boolean propensity; //true -> 적극적투자 , false 소극적투자

    public Member(final String memberEmail, final String memberName, final String memberPassword, final String memberNickName, final int annualIncome, final boolean propensity) {
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.memberPassword = memberPassword;
        this.memberNickName = memberNickName;
        this.annualIncome = annualIncome;
        this.propensity = propensity;
    }

    public void checkPassword(String requestPassword) {
        if (!Objects.equals(memberPassword, requestPassword)) {
            throw new NotSamePasswordException();
        }
    }
}
