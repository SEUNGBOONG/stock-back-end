package com.example.investment_api.virtual.calculator.service;

import com.example.investment_api.virtual.account.controller.dto.AccountStockData;
import com.example.investment_api.virtual.calculator.domain.AllStockCalculator;
import com.example.investment_api.virtual.calculator.domain.StockCalculator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockCalculatorService {

    private final StockCalculator stockCalculator;
    private final AllStockCalculator allStockCalculator;

    public StockCalculatorService(StockCalculator stockCalculator, AllStockCalculator allStockCalculator) {
        this.stockCalculator = stockCalculator;
        this.allStockCalculator = allStockCalculator;
    }

    public double calculateEvaluationProfit(int buyPrice, int currentPrice, int stockCount) {
        return stockCalculator.calculateEvaluationProfit(buyPrice, currentPrice, stockCount);
    }

    public double calculateProfitRate(int buyPrice, int currentPrice) {
        return stockCalculator.calculateProfitRate(buyPrice, currentPrice);
    }

    public int calculatePurchaseAmount(int buyPrice, int stockCount) {
        return stockCalculator.calculatePurchaseAmount(buyPrice, stockCount);
    }

    public double calculateTotalEvaluationProfit(List<AccountStockData> dtoList) {
        return allStockCalculator.calculateTotalEvaluationProfit(dtoList);
    }

    public double calculateTotalPurchaseAmount(List<AccountStockData> dtoList) {
        return allStockCalculator.calculateTotalPurchaseAmount(dtoList);
    }

    public double calculateTotalProfit(List<AccountStockData> dtoList) {
        return allStockCalculator.calculateTotalProfit(dtoList);
    }

    public int calculateTotalEvaluationAmount(List<AccountStockData> dtoList) {
        return allStockCalculator.calculateTotalEvaluationAmount(dtoList);
    }
}
