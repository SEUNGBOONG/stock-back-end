package com.example.investment_api.search.detail.chart.controller;

import com.example.investment_api.search.detail.chart.controller.dto.ChartDTOs;
import com.example.investment_api.search.detail.chart.service.ChartService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search/chart")
public class ChartController {

    private final ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/day")
    public ResponseEntity<ChartDTOs> getDayChart(@RequestParam String stockInfo) throws IOException {
        ChartDTOs chartDTOs = chartService.getChart(stockInfo);
        return ResponseEntity.ok(chartDTOs);
    }

    @GetMapping("/month")
    public ResponseEntity<ChartDTOs> getMonthChart(@RequestParam String stockInfo) throws IOException {
        ChartDTOs chartDTOs = chartService.gerMonthChart(stockInfo);
        return ResponseEntity.ok(chartDTOs);
    }

    @GetMapping("/year")
    public ResponseEntity<ChartDTOs> getYearChart(@RequestParam String stockInfo) throws IOException {
        ChartDTOs chartDTOs = chartService.getYearChart(stockInfo);
        return ResponseEntity.ok(chartDTOs);
    }

    @GetMapping("/week")
    public ResponseEntity<ChartDTOs> getWeekChart(@RequestParam String stockInfo) throws IOException {
        ChartDTOs chartDTOs = chartService.getWeekChart(stockInfo);
        return ResponseEntity.ok(chartDTOs);
    }
}