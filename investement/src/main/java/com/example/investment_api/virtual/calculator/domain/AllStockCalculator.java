package com.example.investment_api.virtual.calculator.domain;

import com.example.investment_api.virtual.calculator.dto.StockCalculationDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component

public class AllStockCalculator {

    private final StockCalculator stockCalculator;

    public AllStockCalculator() {
        this.stockCalculator = new StockCalculator();
    }

    public double calculateTotalEvaluationProfit(List<StockCalculationDTO> dtoList) {
        double totalEvaluationProfit = 0.0;
        for (StockCalculationDTO calculationDTO : dtoList) {
            int currentPrice = calculationDTO.currentPrice();
            int buyPrice = calculationDTO.buyPrice();
            int stockCount = calculationDTO.stockCount();

            totalEvaluationProfit += stockCalculator.calculateEvaluationProfit(currentPrice, buyPrice, stockCount);
        }
        return totalEvaluationProfit; //평가 손익
    }

    public double calculateTotalPurchaseAmount(List<StockCalculationDTO> dtoList) {
        int totalPurchaseAmount = 0;
        for (StockCalculationDTO calculationDTO : dtoList) {
            int buyPrice = calculationDTO.buyPrice();
            int stockCount = calculationDTO.stockCount();
            totalPurchaseAmount += stockCalculator.calculatePurchaseAmount(buyPrice, stockCount);
        }
        return totalPurchaseAmount; //매입 금액
    }

    public double calculateTotalProfit(List<StockCalculationDTO> dtoList) {
        double totalProfit = 0.0;
        double totalPurchaseAmount = calculateTotalPurchaseAmount(dtoList);
        double totalEvaluationProfit = calculateTotalEvaluationProfit(dtoList);
        if (totalPurchaseAmount > 0) {
            totalProfit = (totalEvaluationProfit - totalPurchaseAmount) / totalPurchaseAmount * 100;
        }
        return totalProfit; //수익률
    }

    public int calculateTotalEvaluationAmount(List<StockCalculationDTO> dtolist) {
        int totalEvaluationAmount = 0;
        for (StockCalculationDTO calculationDTO : dtolist) {
            int currentPrice = calculationDTO.currentPrice();
            int stockCount = calculationDTO.stockCount();
            totalEvaluationAmount += stockCalculator.calculateEvaluationAmount(currentPrice, stockCount);
        }
        return totalEvaluationAmount; //평가 금액
    }
}
