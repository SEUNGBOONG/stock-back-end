package com.example.investment_api.virtual.account.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock_orders")
@Getter
@Setter
public class StockOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column
    private String stockName;

    @Column
    private int quantity;

    @Column
    private int limitPrice;

    @Column
    private boolean isBuyOrder; // true: 매수, false: 매도

    protected StockOrder() {

    }

    public StockOrder(Long memberId, String stockName, int quantity, int limitPrice, boolean isBuyOrder) {
        this.memberId = memberId;
        this.stockName = stockName;
        this.quantity = quantity;
        this.limitPrice = limitPrice;
        this.isBuyOrder = isBuyOrder;
    }

}
