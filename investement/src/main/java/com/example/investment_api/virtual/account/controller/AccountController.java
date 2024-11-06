package com.example.investment_api.virtual.account.controller;

import com.example.investment_api.global.annotation.Member;

import com.example.investment_api.virtual.account.controller.dto.BuyRequest;
import com.example.investment_api.virtual.account.controller.dto.BuyResponse;
import com.example.investment_api.virtual.account.controller.dto.LimitOrderResponse;
import com.example.investment_api.virtual.account.controller.dto.OrderRequest;
import com.example.investment_api.virtual.account.controller.dto.SellRequest;
import com.example.investment_api.virtual.account.controller.dto.SellResponse;

import com.example.investment_api.virtual.account.domain.MemberAccount;

import com.example.investment_api.virtual.account.controller.dto.StockOrderDTO;

import com.example.investment_api.virtual.account.service.MemberAccountService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final MemberAccountService memberAccountService;

    public AccountController(MemberAccountService memberAccountService) {
        this.memberAccountService = memberAccountService;
    }

    @PostMapping("/buy")
    public BuyResponse buyStock(@Member Long memberId,
                                @RequestBody BuyRequest request
    ) {
        return memberAccountService.buyStockImmediately(memberId, request.stockName(), request.quantity());
    }

    @PostMapping("/sell")
    public SellResponse sellStock(@Member Long memberId,
                                  @RequestBody SellRequest request
                                  ) {
        return memberAccountService.sellStockImmediately(memberId, request.stockName(), request.quantity());
    }

    @PostMapping("/order/buy")
    public LimitOrderResponse placeLimitOrderForBuy(@Member Long memberId,
                                                    @RequestBody OrderRequest request
                                                    ) {
        return memberAccountService.placeLimitOrderForBuy(memberId, request.stockName(), request.limitPrice(), request.quantity());
    }

    @PostMapping("/order/sell")
    public LimitOrderResponse placeLimitOrderForSell(@Member Long memberId,
                                                     @RequestBody OrderRequest orderRequest) {
        return memberAccountService.placeLimitOrderForSell(memberId, orderRequest.stockName(), orderRequest.limitPrice(), orderRequest.quantity());
    }

    @GetMapping("/{memberId}/accounts")
    public List<MemberAccount> getMemberAccounts(@Member Long memberId) {
        return memberAccountService.getMemberAccounts(memberId);
    }

    @GetMapping("/{memberId}/account/{stockName}")
    public MemberAccount getMemberAccount(@Member Long memberId,
                                          @PathVariable String stockName) {
        return memberAccountService.getMemberAccount(memberId, stockName);
    }

    @PutMapping("/{memberId}/order/limitBuy")
    public String modifyOrder(@Member Long memberId,
                              @RequestBody StockOrderDTO updatedOrder) {
        return memberAccountService.modifyOrder(memberId, updatedOrder);
    }

    @DeleteMapping("/{memberId}/limitSell")
    public String cancelOrder(@Member Long memberId) {
        return memberAccountService.cancelOrder(memberId);
    }
}
