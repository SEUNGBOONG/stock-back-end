package com.example.investment_api.home.sidebar.service;

import com.example.investment_api.home.sidebar.controller.dto.SideBarAccountCount;
import com.example.investment_api.home.sidebar.controller.dto.SideBarAccountDTO;
import com.example.investment_api.home.sidebar.controller.dto.SideBarDTO;

import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.virtual.account.controller.dto.StockData;
import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.exception.StockNotFoundException;
import com.example.investment_api.virtual.account.service.AccountService;
import com.example.investment_api.virtual.account.service.OrderService;

import com.example.investment_api.virtual.calculator.infrastructure.scheduler.AccountDataPollingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SideBarService {

    private final AccountService accountService;

    private final AccountDataPollingService accountDataPollingService;

    private final MemberJpaRepository memberJpaRepository;

    public SideBarService(AccountService accountService, final AccountDataPollingService accountDataPollingService, final MemberJpaRepository memberJpaRepository) {
        this.accountService = accountService;
        this.accountDataPollingService = accountDataPollingService;
        this.memberJpaRepository = memberJpaRepository;
    }

    public List<SideBarDTO> getAccount(Long memberId) {
        List<MemberAccount> accounts = accountService.getMemberAccounts(memberId);
        return accounts.stream()
                .map(this::mapToAccountStockData)
                .collect(Collectors.toList());
    }

    public SideBarAccountCount sendStockCount(Long memberId) {
        int count = getAccount(memberId).size();
        return new SideBarAccountCount(count);
    }

    public SideBarAccountDTO sendAsset(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow();
        int asset = member.getDeposit();
        return new SideBarAccountDTO(asset);
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
