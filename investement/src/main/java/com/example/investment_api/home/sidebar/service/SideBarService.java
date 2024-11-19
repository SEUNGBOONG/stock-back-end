package com.example.investment_api.home.sidebar.service;

import com.example.investment_api.home.sidebar.controller.dto.SideBarAccountCount;
import com.example.investment_api.home.sidebar.controller.dto.SideBarDTO;

import com.example.investment_api.virtual.account.controller.dto.StockData;
import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.exception.StockNotFoundException;
import com.example.investment_api.virtual.account.service.MemberAccountService;

import com.example.investment_api.virtual.calculator.infrastructure.scheduler.AccountDataPollingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SideBarService {

    private final MemberAccountService memberAccountService;

    private final AccountDataPollingService accountDataPollingService;

    public SideBarService(final MemberAccountService memberAccountService, final AccountDataPollingService accountDataPollingService) {
        this.memberAccountService = memberAccountService;
        this.accountDataPollingService = accountDataPollingService;
    }

    public List<SideBarDTO> getAccount(Long memberId) {
        List<MemberAccount> accounts = memberAccountService.getMemberAccounts(memberId);
        return accounts.stream()
                .map(this::mapToAccountStockData)
                .collect(Collectors.toList());
    }

    public SideBarAccountCount sendCount(Long memberId) {
        int count = getAccount(memberId).size();
        return new SideBarAccountCount(count);
    }

    private SideBarDTO mapToAccountStockData(MemberAccount account) {
        String stockName = account.getStockName();
        int currentPrice = getCurrentPrice(stockName);
        double prevChangeRate = getFluctuationData(stockName);
        return new SideBarDTO(
                account.getStockName(),
                currentPrice,
                prevChangeRate
        );
    }

    private int getCurrentPrice(String stockName) {
        return Optional.ofNullable(accountDataPollingService.getLatestStockData(stockName))
                .map(StockData::currentPrice)
                .orElseThrow(StockNotFoundException::new);
    }

    private double getFluctuationData(String stockName) {
        return Optional.ofNullable(accountDataPollingService.getLatestStockData(stockName))
                .map(StockData::prevChangeRate)
                .orElseThrow(StockNotFoundException::new);
    }

}
