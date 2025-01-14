package com.example.investment_api.virtual.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class MemberOrder {

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
    @Column
<<<<<<< HEAD
    private String buyOrder; //true면 매수, false면 매도

    public MemberOrder(Long memberId, String stockName, int buyPrice, int stockCount, String buyOrder) {
=======
    private boolean buyOrder; //true면 매수, false면 매도

    public MemberOrder(Long memberId, String stockName, int buyPrice, int stockCount, boolean buyOrder) {
>>>>>>> origin/171/refactroing/reCode
        this.memberId = memberId;
        this.stockName = stockName;
        this.buyPrice = buyPrice;
        this.stockCount = stockCount;
        this.buyOrder = buyOrder;
    }
}
