package com.example.investment_api.virtual.account.service;

import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.domain.MemberAccountRepository;
import com.example.investment_api.virtual.account.exception.AccountAndStockNotFoundException;
import com.example.investment_api.virtual.account.exception.MemberIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final MemberAccountRepository memberAccountRepository;

    public void saveAccount(Long memberId, String stockName, int stockPrice, int quantity) {
        Optional<MemberAccount> memberAccountOpt = memberAccountRepository.findByMemberIdAndStockName(memberId, stockName);

        if (memberAccountOpt.isPresent()) {
            MemberAccount memberAccount = memberAccountOpt.get();
            memberAccount.addStockCount(quantity);
        } else {
            MemberAccount newAccount = new MemberAccount(memberId, stockName, stockPrice, quantity);
            memberAccountRepository.save(newAccount);
        }
    }

    public MemberAccount getMemberAccount(Long memberId, String stockName) {
        return memberAccountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(() -> new AccountAndStockNotFoundException(memberId, stockName));
    }

    public List<MemberAccount> getMemberAccounts(Long memberId) {
        return memberAccountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberIdNotFoundException(memberId));
    }
}
