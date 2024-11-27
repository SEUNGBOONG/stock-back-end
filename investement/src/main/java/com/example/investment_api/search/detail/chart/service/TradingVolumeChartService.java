package com.example.investment_api.search.detail.chart.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;
import com.example.investment_api.search.detail.chart.controller.dto.TradingVolumeChartDTOs;
import com.example.investment_api.search.detail.chart.domain.TradingVolumeChartParser;
import com.example.investment_api.search.detail.chart.service.client.DayChartFetcher;
import com.example.investment_api.search.detail.chart.service.client.MonthChartFetcher;
import com.example.investment_api.search.detail.chart.service.client.WeekChartFetcher;
import com.example.investment_api.search.detail.chart.service.client.YearChartFetcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TradingVolumeChartService {

    private final TradingVolumeChartParser tradingVolumeChartParser;
    private final DayChartFetcher dayChartFetcher;
    private final MonthChartFetcher monthChartFetcher;
    private final StockRepository stockRepository;
    private final YearChartFetcher yearChartFetcher;
    private final WeekChartFetcher weekChartFetcher;

    public TradingVolumeChartService(TradingVolumeChartParser tradingVolumeChartParser, DayChartFetcher dayChartFetcher, MonthChartFetcher monthChartFetcher, StockRepository stockRepository, YearChartFetcher yearChartFetcher, WeekChartFetcher weekChartFetcher) {
        this.tradingVolumeChartParser = tradingVolumeChartParser;
        this.dayChartFetcher = dayChartFetcher;
        this.monthChartFetcher = monthChartFetcher;
        this.stockRepository = stockRepository;
        this.yearChartFetcher = yearChartFetcher;
        this.weekChartFetcher = weekChartFetcher;
    }

    public TradingVolumeChartDTOs getChart(String stockName) throws IOException {
        return getChartDTOs(stockName);
    }

    public TradingVolumeChartDTOs gerMonthChart(String stockName) throws IOException {
        return getMonthChartDTO(stockName);
    }

    public TradingVolumeChartDTOs getYearChart(String stockName) throws IOException {
        return getYearChartDTO(stockName);
    }

    public TradingVolumeChartDTOs getWeekChart(String stockName) throws IOException {
        return getWeekChartDTO(stockName);
    }

    private TradingVolumeChartDTOs getChartDTOs(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = dayChartFetcher.getDayChartData(stockCode);
        return tradingVolumeChartParser.parseChartData(response.getBody());
    }

    private TradingVolumeChartDTOs getYearChartDTO(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = yearChartFetcher.getYearChartData(stockCode);
        return tradingVolumeChartParser.parseChartData(response.getBody());
    }

    private TradingVolumeChartDTOs getMonthChartDTO(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = monthChartFetcher.getMonthChartData(stockCode);
        return tradingVolumeChartParser.parseChartData(response.getBody());
    }

    private TradingVolumeChartDTOs getWeekChartDTO(final String stockName) throws IOException {
        String stockCode = getStockCodeByName(stockName);
        ResponseEntity<String> response = weekChartFetcher.getWeekChartData(stockCode);
        return tradingVolumeChartParser.parseChartData(response.getBody());
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
