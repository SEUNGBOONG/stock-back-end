package com.example.investment_api.virtual.account.controller;

import com.example.investment_api.global.annotation.Member;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public String buyStock(@Member Long memberId,
                           @RequestParam String stockName,
                           @RequestParam int quantity
    ) {
        return memberAccountService.buyStockImmediately(memberId, stockName, quantity);
    }

    @PostMapping("/sell")
    public String sellStock(@Member Long memberId,
                            @RequestParam String stockName,
                            @RequestParam int quantity) {
        return memberAccountService.sellStockImmediately(memberId, stockName, quantity);
    }

    @PostMapping("/order/buy")
    public String placeLimitOrderForBuy(@Member Long memberId,
                                        @RequestParam String stockName,
                                        @RequestParam int limitPrice,
                                        @RequestParam int quantity) {
        return memberAccountService.placeLimitOrderForBuy(memberId, stockName, limitPrice, quantity);
    }

    @PostMapping("/order/sell")
    public String placeLimitOrderForSell(@Member Long memberId,
                                         @RequestParam String stockName,
                                         @RequestParam int limitPrice,
                                         @RequestParam int quantity) {
        return memberAccountService.placeLimitOrderForSell(memberId, stockName, limitPrice, quantity);
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
