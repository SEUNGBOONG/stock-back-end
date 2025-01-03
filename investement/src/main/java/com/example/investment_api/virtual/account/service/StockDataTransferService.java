package com.example.investment_api.virtual.account.service;

import com.example.investment_api.virtual.account.controller.dto.OrderData;
import com.example.investment_api.virtual.account.controller.dto.UserStockDTO;
import com.example.investment_api.virtual.account.domain.*;

import com.example.investment_api.virtual.account.controller.dto.AccountStockData;
import com.example.investment_api.virtual.account.controller.dto.StockData;
import com.example.investment_api.virtual.account.exception.OrderNotFoundException;
import com.example.investment_api.virtual.account.exception.StockNotFoundException;
import com.example.investment_api.virtual.account.mapper.OrderDataMapper;
import com.example.investment_api.virtual.calculator.infrastructure.scheduler.AccountDataPollingService;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StockDataTransferService {

    private final StockOrderRepository stockOrderRepository;
    private final AccountDataPollingService stockDataPollingService;
    private final AccountService accountService;
    private final OrderDataMapper orderDataMapper;


    public StockDataTransferService(StockOrderRepository stockOrderRepository, AccountDataPollingService stockDataPollingService, OrderDataMapper orderDataMapper, AccountService accountService) {
        this.stockOrderRepository = stockOrderRepository;
        this.stockDataPollingService = stockDataPollingService;
        this.orderDataMapper= orderDataMapper;
        this.accountService = accountService;
    }

    public List<AccountStockData> getAccountStockDataList(Long memberId) {
        List<MemberAccount> accounts = accountService.getMemberAccounts(memberId);

        return accounts.stream()
                .map(account -> {
                    String stockName = account.getStockName();
                    int currentPrice = getCurrentPrice(stockName);
                    double prevChangeRate = getFluctuationData(stockName);
                    return orderDataMapper.mapToAccountStockData(account, currentPrice, prevChangeRate);
                })
                .collect(Collectors.toList());
    }

    public AccountStockData getAccountStockData(Long memberId, String stockName) {
        MemberAccount account = accountService.getMemberAccount(memberId, stockName);
        int currentPrice= getCurrentPrice(stockName);
        double prevChangeRate= getFluctuationData(stockName);
        return orderDataMapper.mapToAccountStockData(account, currentPrice, prevChangeRate);
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

    public List<OrderData> getStockOrderData(Long memberId, String stockName) {
        List<StockOrder> stockOrders = getStockOrders(memberId, stockName);
        int totalQuantity = stockOrders.stream()
                .mapToInt(StockOrder::getQuantity)
                .sum();
        return stockOrders.stream()
                .map(order -> orderDataMapper.mapToOrderDTO(order, totalQuantity))
                .collect(Collectors.toList());
    }

    public List<StockOrder> getStockOrders(Long memberId, String stockName){
        return stockOrderRepository.findByMemberIdAndStockName(memberId,stockName)
                .orElseThrow(OrderNotFoundException::new);
    }

    public UserStockDTO getUserStockNames(Long memberId){
        List<MemberAccount> memberAccounts = accountService.getMemberAccounts(memberId);
        List<String> stockNames = memberAccounts.stream()
                .map(MemberAccount::getStockName)
                .toList();
        return new UserStockDTO(stockNames);
    }

}