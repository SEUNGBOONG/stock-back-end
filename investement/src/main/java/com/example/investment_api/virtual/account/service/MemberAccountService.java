package com.example.investment_api.virtual.account.service;

import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.domain.MemberAccountRepository;
import com.example.investment_api.virtual.account.dto.AccountDataDTO;
import com.example.investment_api.virtual.account.dto.StockCalculationDTO;
import com.example.investment_api.virtual.account.exception.NoSuchStock;
import com.example.investment_api.virtual.calculator.infrastructure.scheduler.AccountDataPollingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberAccountService {

    private final MemberAccountRepository accountRepository;
    private final AccountDataPollingService stockDataPollingService;

    public MemberAccountService(MemberAccountRepository accountRepository, AccountDataPollingService stockDataPollingService) {
        this.accountRepository = accountRepository;
        this.stockDataPollingService = stockDataPollingService;
    }

    public List<StockCalculationDTO> getStockCalculationDtoList(Long memberId) {
        List<MemberAccount> accounts = getMemberAccounts(memberId);

        return accounts.stream().map(account -> {
            String stockName = account.getStockName();
            int currentPrice = getCurrentPrice(stockName);
            return new StockCalculationDTO(account.getStockName(), account.getBuyPrice(), account.getStockCount(), currentPrice);
        }).collect(Collectors.toList());
    }

    public List<MemberAccount> getMemberAccounts(Long memberId) {
        return accountRepository.findByMemberId(memberId)
                .orElseThrow(RuntimeException::new);
    }

    public StockCalculationDTO getStockCalculationDTOList(Long memberId, String stockName) {
        MemberAccount account = getMemberAccount(memberId, stockName);
        int currentPrice = getCurrentPrice(stockName);
        return new StockCalculationDTO(account.getStockName(), account.getBuyPrice(), account.getStockCount(), currentPrice);
    }

    private int getCurrentPrice(String stockName) {
        return Optional.ofNullable(stockDataPollingService.getLatestStockData(stockName))
                .map(AccountDataDTO::currentPrice)
                .orElseThrow(() -> new NoSuchStock(stockName));
    }

    public double getFluctuationData(String stockName) {
        return Optional.ofNullable(stockDataPollingService.getLatestStockData(stockName))
                .map(AccountDataDTO::prevChangeRate)
                .orElseThrow(() -> new NoSuchStock(stockName));
    }

    public MemberAccount getMemberAccount(Long memberId, String stockName) {
        return accountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(RuntimeException::new);
    }
}
