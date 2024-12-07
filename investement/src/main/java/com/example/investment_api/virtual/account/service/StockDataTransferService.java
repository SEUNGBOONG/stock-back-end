package com.example.investment_api.virtual.account.service;

import com.example.investment_api.virtual.account.domain.MemberAccount;

import com.example.investment_api.virtual.account.controller.dto.AccountStockData;
import com.example.investment_api.virtual.account.controller.dto.StockData;
import com.example.investment_api.virtual.account.exception.StockNotFoundException;
import com.example.investment_api.virtual.calculator.infrastructure.scheduler.AccountDataPollingService;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StockDataTransferService {

    private final AccountDataPollingService stockDataPollingService;
    private final MemberAccountService memberAccountService;

    public StockDataTransferService(AccountDataPollingService stockDataPollingService, MemberAccountService memberAccountService) {
        this.stockDataPollingService = stockDataPollingService;
        this.memberAccountService = memberAccountService;
    }

    public List<AccountStockData> getAccountStockDataList(Long memberId) {
        List<MemberAccount> accounts = memberAccountService.getMemberAccounts(memberId);

        return accounts.stream()
                .map(this::mapToAccountStockData)
                .collect(Collectors.toList());
    }

    public AccountStockData getAccountStockData(Long memberId, String stockName) {
        MemberAccount account = memberAccountService.getMemberAccount(memberId, stockName);
        return mapToAccountStockData(account);
    }

    private AccountStockData mapToAccountStockData(MemberAccount account) {
        String stockName = account.getStockName();
        int currentPrice = getCurrentPrice(stockName);
        double prevChangeRate = getFluctuationData(stockName);

        return new AccountStockData(
                account.getStockName(),
                account.getBuyPrice(),
                account.getStockCount(),
                currentPrice,
                prevChangeRate
        );
    }

    private int getCurrentPrice(String stockName) {
        return Optional.ofNullable(stockDataPollingService.getLatestStockData(stockName))
                .map(StockData::currentPrice)
                .orElseThrow(StockNotFoundException::new);
    }

    private double getFluctuationData(String stockName) {
        return Optional.ofNullable(stockDataPollingService.getLatestStockData(stockName))
                .map(StockData::prevChangeRate)
                .orElseThrow(StockNotFoundException::new);
    }
}