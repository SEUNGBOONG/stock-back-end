package com.example.investment_api.virtual.account.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;

import com.example.investment_api.member.domain.member.MemberDeposit;
import com.example.investment_api.member.exception.exceptions.member.NotFoundMemberDepositException;
import com.example.investment_api.member.infrastructure.member.MemberDepositJpaRepository;
import com.example.investment_api.search.detail.stock.service.client.StockDataFetcher;

import com.example.investment_api.virtual.account.controller.dto.BuyResponse;
import com.example.investment_api.virtual.account.controller.dto.SellResponse;
import com.example.investment_api.virtual.account.controller.dto.LimitOrderResponse;
import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.domain.MemberAccountRepository;
import com.example.investment_api.virtual.account.domain.StockOrderRepository;

import com.example.investment_api.virtual.account.domain.StockOrder;

import com.example.investment_api.virtual.account.controller.dto.StockDataDTO;
import com.example.investment_api.virtual.account.controller.dto.StockOrderDTO;

import com.example.investment_api.virtual.account.exception.NoSuchAccount;
import com.example.investment_api.virtual.account.exception.NoSuchAccountMemberId;
import com.example.investment_api.virtual.account.exception.NoSuchMemberIdException;
import com.example.investment_api.virtual.account.exception.NotFoundOrderException;
import com.example.investment_api.virtual.account.infrastructure.AccountStockParser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberAccountService {

    private final MemberAccountRepository memberAccountRepository;
    private final StockOrderRepository stockOrderRepository;
    private final StockRepository stockRepository;
    private final MemberDepositJpaRepository depositJpaRepository;

    private final StockDataFetcher stockDataFetcher;
    private final AccountStockParser accountStockParser;

    public BuyResponse buyStockImmediately(Long memberId, String stockName, int quantity) {
        int currentPrice = getCurrentPrice(stockName);
        MemberDeposit deposit = depositJpaRepository.findByMemberId(memberId)
                .orElseThrow(NotFoundMemberDepositException::new);
        deposit.calculateDeposit(currentPrice, quantity);

        saveAccount(memberId, stockName, currentPrice, quantity);

        return new BuyResponse(memberId, stockName, currentPrice, quantity, deposit.getDeposit());
    }

    @Transactional
    public SellResponse sellStockImmediately(Long memberId, String stockName, int quantity) {
        int currentPrice = getCurrentPrice(stockName);
        MemberAccount memberAccount = memberAccountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(NoSuchAccount::new);

        if (memberAccount.getStockCount() >= quantity) {
            memberAccount.removeStockCount(quantity);
            memberAccountRepository.save(memberAccount);
            if (memberAccount.getStockCount() == 0) {
                memberAccountRepository.delete(memberAccount);
            }
            int remainNumbers = memberAccount.getStockCount();
            return new SellResponse(memberId, stockName, currentPrice, remainNumbers);
        }
        throw new RuntimeException("보유 주식 수량이 부족합니다.");

    }

    @Transactional
    public LimitOrderResponse placeLimitOrderForBuy(Long memberId, String stockName, int limitPrice, int quantity) {
        StockOrder order = new StockOrder(memberId, stockName, quantity, limitPrice, true);
        stockOrderRepository.save(order);
        return new LimitOrderResponse(memberId, stockName, limitPrice, quantity);
    }

    @Transactional
    public LimitOrderResponse placeLimitOrderForSell(Long memberId, String stockName, int limitPrice, int quantity) {
        StockOrder order = new StockOrder(memberId, stockName, quantity, limitPrice, false);
        stockOrderRepository.save(order);
        return new LimitOrderResponse(memberId, stockName, limitPrice, quantity);
    }

    private void saveAccount(final Long memberId, final String stockName, final int stockPrice, final int quantity) {
        Optional<MemberAccount> memberAccountOpt = memberAccountRepository.findByMemberIdAndStockName(memberId, stockName);

        if (memberAccountOpt.isPresent()) {
            MemberAccount memberAccount = memberAccountOpt.get();
            memberAccount.calculateNewStockPrice(stockPrice, quantity);
            memberAccount.addStockCount(quantity);
        } else {
            MemberAccount newAccount = new MemberAccount(memberId, stockName, stockPrice, quantity);
            memberAccountRepository.save(newAccount);
        }
    }

    public List<MemberAccount> getMemberAccounts(Long memberId) {
        return memberAccountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchMemberIdException(memberId));
    }

    public MemberAccount getMemberAccount(Long memberId, String stockName) {
        return memberAccountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(() -> new NoSuchAccountMemberId(memberId, stockName));
    }

    public String modifyOrder(Long memberId, StockOrderDTO updatedOrder) {
        StockOrder order = stockOrderRepository.findById(memberId)
                .orElseThrow(NotFoundOrderException::new);
        order.setStockName(updatedOrder.getStockName());
        order.setQuantity(updatedOrder.getQuantity());
        order.setLimitPrice(updatedOrder.getPrice());
        stockOrderRepository.save(order);

        return "주문이 수정되었습니다: " + updatedOrder.getStockName();
    }

    public String cancelOrder(Long memberId) {
        StockOrder order = stockOrderRepository.findById(memberId)
                .orElseThrow(NotFoundOrderException::new);
        stockOrderRepository.delete(order);
        return "주문이 취소되었습니다: " + order.getStockName();
    }

    private int getCurrentPrice(String stockName) {
        Stock stock = stockRepository.findByStockName(stockName)
                .orElseThrow(() -> new RuntimeException("주식명: " + stockName + "을(를) 찾을 수 없습니다."));
        ResponseEntity<String> response = stockDataFetcher.fetchStockData(stock.getStockCode());

        try {
            StockDataDTO stockData = accountStockParser.parse(response.getBody());
            return Integer.parseInt(stockData.getCurrentPrice());
        } catch (IOException e) {
            throw new RuntimeException("현재가 정보를 가져오는 중 오류 발생: " + e.getMessage());
        }
    }

}
