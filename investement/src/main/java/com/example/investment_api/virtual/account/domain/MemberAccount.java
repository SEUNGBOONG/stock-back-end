package com.example.investment_api.virtual.account.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemberAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column
    private String stockName;

    @Column
    private int buyPrice;

    @Column
    private int stockCount;

    public MemberAccount(Long memberId, String stockName, int buyPrice, int stockCount) {
        this.memberId = memberId;
        this.stockName = stockName;
        this.buyPrice = buyPrice;
        this.stockCount = stockCount;
    }

    public void addStockCount(int count) {
        this.stockCount += count;
    }

    public void removeStockCount(int count) {
        this.stockCount -= count;
    }


}
