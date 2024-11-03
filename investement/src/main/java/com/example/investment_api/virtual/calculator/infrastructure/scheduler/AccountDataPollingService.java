package com.example.investment_api.virtual.calculator.infrastructure.scheduler;

import com.example.investment_api.home.marketCapitalization.service.client.MarketCapitalizationFetcher;
import com.example.investment_api.virtual.account.dto.StockData;
import com.example.investment_api.virtual.calculator.infrastructure.AccountDataParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountDataPollingService {

    private final MarketCapitalizationFetcher dataFetcher;
    private final AccountDataParser accountDataParser;
    private final Map<String, StockData> latestStockData = new ConcurrentHashMap<>();

    public AccountDataPollingService(MarketCapitalizationFetcher fluctuationDataFetcher, AccountDataParser accountDataParser) {
        this.dataFetcher = fluctuationDataFetcher;
        this.accountDataParser = accountDataParser;
    }

    @Scheduled(fixedRate = 10000) //10초
    public void pollFluctuationData() throws Exception {
        String responseBody = dataFetcher.marketCapitalizationData().getBody();
        List<StockData> stockDataList = accountDataParser.parseAll(responseBody);

        if (stockDataList != null && !stockDataList.isEmpty()) {
            // 각 주식 이름을 키로 하여 최신 데이터를 저장
            for (StockData stockData : stockDataList) {
                latestStockData.put(stockData.stockName(), stockData);
            }
        }
    }

    public StockData getLatestStockData(String stockName) {
        return latestStockData.get(stockName);
    }
}