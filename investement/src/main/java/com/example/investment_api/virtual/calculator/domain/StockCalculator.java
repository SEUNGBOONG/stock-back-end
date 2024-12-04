package com.example.investment_api.virtual.calculator.domain;

import org.springframework.stereotype.Component;

@Component
public class StockCalculator {

    public double calculateEvaluationProfit(int buyPrice, int currentPrice, int stockCount) {
        return (buyPrice - currentPrice) * stockCount;
    } // 평가손익

    public double calculateProfitRate(int buyPrice, int currentPrice) {
        return ((double) (currentPrice - buyPrice) / buyPrice) * 100;
    } //수익률

    public int calculatePurchaseAmount(int buyPrice, int stockCount) {
        return buyPrice * stockCount;
    } //매입 금액

    public int calculateEvaluationAmount(int currentPrice, int stockCount) {
        return currentPrice * stockCount;
    } //평가 금액
}
