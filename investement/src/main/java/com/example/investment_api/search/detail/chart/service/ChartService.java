package com.example.investment_api.search.detail.chart.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;
import com.example.investment_api.search.detail.chart.controller.dto.ChartDTOs;
import com.example.investment_api.search.detail.chart.domain.ChartParser;
import com.example.investment_api.search.detail.chart.service.client.DayChartFetcher;
import jakarta.transaction.Transactional;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChartService {

    private final ChartParser chartParser;
    private final DayChartFetcher dayChartFetcher;
    private final StockRepository stockRepository;

    @Autowired
    public ChartService(ChartParser chartParser, DayChartFetcher dayChartFetcher, final StockRepository stockRepository) {
        this.chartParser = chartParser;
        this.dayChartFetcher = dayChartFetcher;
        this.stockRepository = stockRepository;
    }

    public ChartDTOs getChart(String stockName) throws IOException {
        return getChartDTOs(stockName);
    }

    private ChartDTOs getChartDTOs(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = dayChartFetcher.getDayChartData(stockCode);
        return chartParser.parseChartData(response.getBody());
    }

    private String getStockCodeByName(String stockName) {
        return stockRepository.findByStockName(stockName)
                .map(Stock::getStockCode)
                .orElseThrow(() -> {
                    System.out.println("주식명: " + stockName + "을(를) 찾을 수 없습니다.");
                    return new RuntimeException("해당 주식명을 찾을 수 없습니다: " + stockName);
                });
    }
}
