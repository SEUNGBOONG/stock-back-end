package com.example.investment_api.search.detail.enterpriseInfo.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;
import com.example.investment_api.search.detail.enterpriseInfo.controller.dto.EnterpriseDTO;
import com.example.investment_api.search.detail.enterpriseInfo.infrastructure.EnterpriseParser;
import com.example.investment_api.search.detail.enterpriseInfo.service.client.EnterpriseFetcher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class EnterpriseService {

    private final StockRepository stockRepository;
    private final EnterpriseFetcher enterpriseFetcher;
    private final EnterpriseParser enterpriseParser;

    @Autowired
    public EnterpriseService(final StockRepository stockRepository, final EnterpriseFetcher enterpriseFetcher, final EnterpriseParser enterpriseParser) {
        this.stockRepository = stockRepository;
        this.enterpriseFetcher = enterpriseFetcher;
        this.enterpriseParser = enterpriseParser;
    }

    public List<EnterpriseDTO> getFinancialRatio(String stockName) throws IOException {
        return getFinancialRatioDTOS(stockName);
    }

    private List<EnterpriseDTO> getFinancialRatioDTOS(final String stockName) throws IOException {
        Stock stock = stockRepository.findByStockName(stockName)
                .orElseThrow(() -> new RuntimeException("주식명: " + stockName + "을(를) 찾을 수 없습니다."));
        ResponseEntity<String> response = enterpriseFetcher.fetchEnterpriseData(stock.getStockCode());
        return enterpriseParser.parseEnterpriseInfo(response.getBody());
    }
}
