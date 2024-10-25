package com.example.investment_api.common.stockData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="STOCK_INFO")
public class Stock {

    @Id
    @NotNull
    private String stockCode;

    @Column
    private String stockName;
}
