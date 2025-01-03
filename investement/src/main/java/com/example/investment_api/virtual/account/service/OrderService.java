package com.example.investment_api.virtual.account.service;

import com.example.investment_api.common.stockData.Stock;
import com.example.investment_api.common.stockData.StockRepository;

import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.exception.exceptions.member.NotFoundMemberDepositException;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.search.detail.stock.service.client.StockDataFetcher;

import com.example.investment_api.virtual.account.controller.dto.*;
import com.example.investment_api.virtual.account.domain.*;

import com.example.investment_api.virtual.account.exception.*;
import com.example.investment_api.virtual.account.infrastructure.AccountStockParser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final MemberAccountRepository memberAccountRepository;
    private final StockOrderRepository stockOrderRepository;
    private final StockRepository stockRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final MemberOrderRepository memberOrderRepository;

    private final StockDataFetcher stockDataFetcher;
    private final AccountStockParser accountStockParser;

    private final AccountService accountService;

    public BuyResponse buyStockImmediately(Long memberId, String stockName, int quantity) {
        validateQuantity(quantity);
        int currentPrice = getCurrentPrice(stockName);
        Member member = getMember(memberId);
        member.calculateDeposit(currentPrice, quantity);

        accountService.saveAccount(memberId, stockName, currentPrice, quantity);
        MemberOrder memberOrder= new MemberOrder(memberId, stockName, currentPrice, quantity, "매수");
        memberOrderRepository.save(memberOrder);

        return new BuyResponse(memberId, stockName, currentPrice, quantity, member.getDeposit());
    }

    @Transactional
    public SellResponse sellStockImmediately(Long memberId, String stockName, int quantity) {
        int currentPrice = getCurrentPrice(stockName);
        validateQuantity(quantity);
        MemberAccount memberAccount = assumeNotStockGetAccount(memberId, stockName);
        Member member = getMember(memberId);
        if (memberAccount.getStockCount() >= quantity) {
            memberAccount.removeStockCount(quantity);
            memberAccountRepository.save(memberAccount);
            deleteEmptyStock(memberAccount);
            int remainStockCounts = memberAccount.getStockCount();
            member.calculateSellDeposit(currentPrice, quantity);
            MemberOrder memberOrder= new MemberOrder(memberId, stockName, currentPrice, quantity, "매도");
            memberOrderRepository.save(memberOrder);
            return new SellResponse(memberId, stockName, currentPrice, remainStockCounts);
        }
        throw new InsufficientStockQuantityException();
    }

    private MemberAccount assumeNotStockGetAccount(Long memberId, String stockName) {
        return memberAccountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(InsufficientStockQuantityException::new);
    }

    private void deleteEmptyStock(MemberAccount memberAccount) {
        if (memberAccount.getStockCount() == 0) {
            memberAccountRepository.delete(memberAccount);
        }
    }

    @Transactional
    public LimitOrderResponse placeLimitOrderForBuy(Long memberId, String stockName, int limitPrice, int quantity) {
        StockOrder order = new StockOrder(memberId, stockName, quantity, limitPrice, "매수");
        stockOrderRepository.save(order);
        return new LimitOrderResponse(memberId, stockName, limitPrice, quantity, order.getIsBuyOrder());
    }

    @Transactional
    public LimitOrderResponse placeLimitOrderForSell(Long memberId, String stockName, int limitPrice, int quantity) {
        StockOrder order = new StockOrder(memberId, stockName, quantity, limitPrice, "매도");
        stockOrderRepository.save(order);
        return new LimitOrderResponse(memberId, stockName, limitPrice, quantity, order.getIsBuyOrder());
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException();
        }
    }

    private Member getMember(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(NotFoundMemberDepositException::new);
    }

    public String modifyOrder(Long memberId, int orderId, StockOrderDTO updatedOrder) {
        StockOrder order = stockOrderRepository.findByMemberIdAndId(memberId, (long) orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.setStockName(updatedOrder.stockName());
        order.setQuantity(updatedOrder.quantity());
        order.setLimitPrice(updatedOrder.limitPrice());
        stockOrderRepository.save(order);

        return "주문이 수정되었습니다: " + updatedOrder.stockName();
    }

    public String cancelOrder(Long memberId, int orderId) {
        StockOrder order = stockOrderRepository
                .findByMemberIdAndId(memberId, (long) orderId)
                .orElseThrow(OrderNotFoundException::new);
        stockOrderRepository.delete(order);

        return "주문이 취소되고 삭제되었습니다: " + order.getStockName() + " 주식, 가격: " + order.getLimitPrice();
    }

    public int getCurrentPrice(String stockName) {
        Stock stock = stockRepository.findByStockName(stockName)
                .orElseThrow(StockNotFoundException::new);
        ResponseEntity<String> response = stockDataFetcher.fetchStockData(stock.getStockCode());

        try {
            StockDataDTO stockData = accountStockParser.parse(response.getBody());
            return Integer.parseInt(stockData.getCurrentPrice());
        } catch (IOException e) {
            throw new RuntimeException("현재가 정보를 가져오는 중 오류 발생: " + e.getMessage());
        }
    }


    public List<MemberOrder> getMemberOrders(Long memberId, String stockName){
        return memberOrderRepository.findMemberOrdersByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(OrderNotFoundException::new);
    }
}
