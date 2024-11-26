package com.example.investment_api.search.detail.chart.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;

import com.example.investment_api.search.detail.chart.controller.dto.ChartDTOs;
import com.example.investment_api.search.detail.chart.domain.ChartParser;
import com.example.investment_api.search.detail.chart.service.client.DayChartFetcher;
import com.example.investment_api.search.detail.chart.service.client.MonthChartFetcher;
import com.example.investment_api.search.detail.chart.service.client.WeekChartFetcher;
import com.example.investment_api.search.detail.chart.service.client.YearChartFetcher;

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
    private final MonthChartFetcher monthChartFetcher;
    private final StockRepository stockRepository;
    private final YearChartFetcher yearChartFetcher;
    private final WeekChartFetcher weekChartFetcher;

    @Autowired
    public ChartService(final ChartParser chartParser, final DayChartFetcher dayChartFetcher, final MonthChartFetcher monthChartFetcher, final StockRepository stockRepository, final YearChartFetcher yearChartFetcher, final WeekChartFetcher weekChartFetcher) {
        this.chartParser = chartParser;
        this.dayChartFetcher = dayChartFetcher;
        this.monthChartFetcher = monthChartFetcher;
        this.stockRepository = stockRepository;
        this.yearChartFetcher = yearChartFetcher;
        this.weekChartFetcher = weekChartFetcher;
    }

    public ChartDTOs getChart(String stockName) throws IOException {
        return getChartDTOs(stockName);
    }

    public ChartDTOs gerMonthChart(String stockName) throws IOException {
        return getMonthChartDTO(stockName);
    }

    public ChartDTOs getYearChart(String stockName) throws IOException {
        return getYearChartDTO(stockName);
    }

    public ChartDTOs getWeekChart(String stockName) throws IOException {
        return getWeekChartDTO(stockName);
    }

    private ChartDTOs getChartDTOs(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = dayChartFetcher.getDayChartData(stockCode);
        return chartParser.parseChartData(response.getBody());
    }

    private ChartDTOs getYearChartDTO(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = yearChartFetcher.getYearChartData(stockCode);
        return chartParser.parseChartData(response.getBody());
    }

    private ChartDTOs getMonthChartDTO(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = monthChartFetcher.getMonthChartData(stockCode);
        return chartParser.parseChartData(response.getBody());
    }

    private ChartDTOs getWeekChartDTO(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = weekChartFetcher.getWeekChartData(stockCode);
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
