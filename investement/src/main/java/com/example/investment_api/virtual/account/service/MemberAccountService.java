package com.example.investment_api.virtual.account.service;

import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.domain.MemberAccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class MemberAccountService {

    private final MemberAccountRepository accountRepository;

    public MemberAccountService(MemberAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<MemberAccount> getMemberAccounts(Long memberId) {
        return accountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("No accounts found for memberId: " + memberId));
    }

    public MemberAccount getMemberAccount(Long memberId, String stockName) {
        return accountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(() -> new NoSuchElementException("Account not found for memberId: " + memberId + " and stockName: " + stockName));
    }
}
