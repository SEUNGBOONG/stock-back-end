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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberAccountService {

    private final MemberAccountRepository memberAccountRepository;
    private final StockOrderRepository stockOrderRepository;
    private final StockRepository stockRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final MemberOrderRepository memberOrderRepository;


    private final StockDataFetcher stockDataFetcher;
    private final AccountStockParser accountStockParser;

    public BuyResponse buyStockImmediately(Long memberId, String stockName, int quantity) {
        validateQuantity(quantity);
        int currentPrice = getCurrentPrice(stockName);
        Member member = getMember(memberId);
        member.calculateDeposit(currentPrice, quantity);

        saveAccount(memberId, stockName, currentPrice, quantity);
        MemberOrder memberOrder= new MemberOrder(memberId, stockName, currentPrice, quantity, true);
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
            MemberOrder memberOrder= new MemberOrder(memberId, stockName, currentPrice, quantity, false);
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
        StockOrder order = new StockOrder(memberId, stockName, quantity, limitPrice, OrderType.BUY.getType());
        stockOrderRepository.save(order);
        return new LimitOrderResponse(memberId, stockName, limitPrice, quantity, order.getIsBuyOrder());
    }

    @Transactional
    public LimitOrderResponse placeLimitOrderForSell(Long memberId, String stockName, int limitPrice, int quantity) {
        StockOrder order = new StockOrder(memberId, stockName, quantity, limitPrice, OrderType.SELL.getType());
        stockOrderRepository.save(order);
        return new LimitOrderResponse(memberId, stockName, limitPrice, quantity, order.getIsBuyOrder());
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void executePendingOrders() {
        List<StockOrder> pendingOrders = stockOrderRepository.findByIsProcessedFalse();
        for (StockOrder order : pendingOrders) {
            Long memberId = order.getMemberId();
            String stockName = order.getStockName();
            int limitPrice = order.getLimitPrice();
            int quantity = order.getQuantity();
            int currentPrice = getCurrentPrice(stockName);

            processStockOrder(order, memberId, stockName, limitPrice, quantity, currentPrice);
        }
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

    private void processStockOrder(StockOrder order, Long memberId, String stockName, int limitPrice, int quantity, int currentPrice) {
        if (currentPrice == limitPrice) {
            if (order.getIsBuyOrder().equals(OrderType.BUY.getType())) {
                buyStockImmediately(memberId, stockName, quantity);
            } else {
                sellStockImmediately(memberId, stockName, quantity);
            }
            stockOrderRepository.save(order);
        }
    }

    private void saveAccount(final Long memberId, final String stockName, final int stockPrice, final int quantity) {
        Optional<MemberAccount> memberAccountOpt = memberAccountRepository.findByMemberIdAndStockName(memberId, stockName);

        if (memberAccountOpt.isPresent()) {
            MemberAccount memberAccount = memberAccountOpt.get();
            memberAccount.addStockCount(quantity);
        } else {
            MemberAccount newAccount = new MemberAccount(memberId, stockName, stockPrice, quantity);
            memberAccountRepository.save(newAccount);
        }
    }

    public List<MemberAccount> getMemberAccounts(Long memberId) {
        return memberAccountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberIdNotFoundException(memberId));
    }

    public MemberAccount getMemberAccount(Long memberId, String stockName) {
        return memberAccountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(() -> new AccountAndStockNotFoundException(memberId, stockName));
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

    private int getCurrentPrice(String stockName) {
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

    public UserStockDTO getUserStockNames(Long memberId){
        List<MemberAccount> memberAccounts = getMemberAccounts(memberId);
        List<String> stockNames = memberAccounts.stream()
                .map(MemberAccount::getStockName)
                .toList();
        return new UserStockDTO(stockNames);
    }

    public List<StockOrder> getStockOrders(Long memberId, String stockName){
        return stockOrderRepository.findByMemberIdAndStockName(memberId,stockName)
                .orElseThrow(OrderNotFoundException::new);
    }

    public List<OrderData> getStockOrderData(Long memberId, String stockName) {
        List<StockOrder> stockOrders = getStockOrders(memberId, stockName);
        int totalQuantity = stockOrders.stream()
                .mapToInt(StockOrder::getQuantity)
                .sum();
        return stockOrders.stream()
                .map(order -> mapToOrderDTO(order, totalQuantity))
                .collect(Collectors.toList());
    }

    public OrderData mapToOrderDTO(StockOrder stockOrder, int totalQuantity) {
        int remainQuantity = totalQuantity - stockOrder.getQuantity();
        return new OrderData(
                stockOrder.getId(),
                stockOrder.getStockName(),
                remainQuantity,
                stockOrder.getQuantity(),
                stockOrder.getLimitPrice(),
                stockOrder.getIsBuyOrder()
        );
    }

    public List<MemberOrder> getMemberOrders(Long memberId, String stockName){
        return memberOrderRepository.findMemberOrdersByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(OrderNotFoundException::new);
    }
}
