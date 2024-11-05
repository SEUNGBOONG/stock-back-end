package com.example.investment_api.virtual.account.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;

import com.example.investment_api.search.detail.stock.service.client.StockDataFetcher;

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

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class MemberAccountService {

    private final MemberAccountRepository memberAccountRepository;
    private final StockOrderRepository stockOrderRepository;
    private final StockRepository stockRepository;

    private final StockDataFetcher stockDataFetcher;
    private final AccountStockParser accountStockParser;

    public MemberAccountService(final MemberAccountRepository memberAccountRepository,
                                final AccountStockParser accountStockParser, final StockOrderRepository stockOrderRepository,
                                final StockRepository stockRepository,
                                final StockDataFetcher stockDataFetcher) {
        this.memberAccountRepository = memberAccountRepository;
        this.accountStockParser = accountStockParser;
        this.stockOrderRepository = stockOrderRepository;
        this.stockRepository = stockRepository;
        this.stockDataFetcher = stockDataFetcher;
    }

    @Transactional
    public String buyStockImmediately(Long memberId, String stockName, int quantity) {
        int currentPrice = getCurrentPrice(stockName);
        return processBuyStock(memberId, stockName, currentPrice, quantity);
    }

    @Transactional
    public String sellStockImmediately(Long memberId, String stockName, int quantity) {
        return processSellStock(memberId, stockName, getCurrentPrice(stockName), quantity);
    }

    @Transactional
    public String placeLimitOrderForBuy(Long memberId, String stockName, int limitPrice, int quantity) {
        StockOrder order = new StockOrder(memberId, stockName, quantity, limitPrice, true);
        stockOrderRepository.save(order);
        return "지정가 매수 주문이 접수되었습니다: " + stockName + ", 가격: " + limitPrice + ", 수량: " + quantity;
    }

    @Transactional
    public String placeLimitOrderForSell(Long memberId, String stockName, int limitPrice, int quantity) {
        StockOrder order = new StockOrder(memberId, stockName, quantity, limitPrice, false);
        stockOrderRepository.save(order);
        return "지정가 매도 주문이 접수되었습니다: " + stockName + ", 가격: " + limitPrice + ", 수량: " + quantity;
    }

    private String processBuyStock(Long memberId, String stockName, int targetBuyPrice, int quantity) {
        MemberAccount memberAccount = memberAccountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseGet(() -> {
                    MemberAccount newAccount = new MemberAccount(memberId, stockName, targetBuyPrice, 0);
                    memberAccountRepository.save(newAccount);
                    return newAccount;
                });

        memberAccount.addStockCount(quantity);
        memberAccountRepository.save(memberAccount);
        return "주식 매수 완료: " + stockName + ", 수량: " + quantity;
    }

    private String processSellStock(Long memberId, String stockName, int targetSellPrice, int quantity) {
        MemberAccount memberAccount = memberAccountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(NoSuchAccount::new);
        return perfectSell(stockName, targetSellPrice, quantity, memberAccount);
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

    private String perfectSell(final String stockName, final int targetSellPrice, final int quantity, final MemberAccount memberAccount) {
        if (memberAccount.getStockCount() >= quantity) {
            memberAccount.removeStockCount(quantity);
            if (memberAccount.getStockCount() == 0) {
                memberAccountRepository.delete(memberAccount);
            } else {
                memberAccountRepository.save(memberAccount);
            }
            return "주식 매도 완료: " + stockName + ", 수량: " + quantity + ", 매도 가격: " + targetSellPrice;
        }
        return "보유 주식 수량이 부족합니다.";
    }
}
