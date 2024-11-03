package com.example.investment_api.virtual.calculator.mapper;

import com.example.investment_api.virtual.account.dto.AllResultDTO;
import com.example.investment_api.virtual.account.dto.AccountStockData;
import com.example.investment_api.virtual.account.dto.resultDTO;
import com.example.investment_api.virtual.calculator.domain.AllStockCalculator;
import com.example.investment_api.virtual.calculator.domain.StockCalculator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockCalculationMapper {

    private final StockCalculator stockCalculator;
    private final AllStockCalculator allStockCalculator;

    public StockCalculationMapper(StockCalculator stockCalculator, AllStockCalculator allStockCalculator) {
        this.stockCalculator = stockCalculator;
        this.allStockCalculator = allStockCalculator;
    }

    public resultDTO toResultDTO(String stockName, AccountStockData dto) {
        double evaluationProfit = stockCalculator.calculateEvaluationProfit(dto.buyPrice(), dto.currentPrice(), dto.stockCount());
        double profitRate = stockCalculator.calculateProfitRate(dto.buyPrice(), dto.currentPrice());
        int purchaseAmount = stockCalculator.calculatePurchaseAmount(dto.buyPrice(), dto.stockCount());
        int evaluationAmount = stockCalculator.calculateEvaluationAmount(dto.currentPrice(), dto.stockCount());

        return new resultDTO(
                stockName,
                dto.currentPrice(),
                dto.stockCount(),
                dto.prevChangeRate(),
                evaluationProfit,
                profitRate,
                purchaseAmount,
                evaluationAmount
        );
    }

    public AllResultDTO toAllResultDTO(List<AccountStockData> dtoList) {
        double totalEvaluationProfit = allStockCalculator.calculateTotalEvaluationProfit(dtoList);
        double totalPurchaseAmount = allStockCalculator.calculateTotalPurchaseAmount(dtoList);
        double totalProfit = allStockCalculator.calculateTotalProfit(dtoList);
        int totalEvaluationAmount = allStockCalculator.calculateTotalEvaluationAmount(dtoList);

        return new AllResultDTO(totalEvaluationProfit, totalPurchaseAmount, totalProfit, totalEvaluationAmount);
    }
}
