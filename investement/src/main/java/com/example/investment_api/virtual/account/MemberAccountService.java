package com.example.investment_api.virtual.account;

import com.example.investment_api.virtual.calculator.dto.StockCalculationDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberAccountService {

    private final MemberAccountRepository accountRepository;

    public MemberAccountService(MemberAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<StockCalculationDTO> getStockCalculationDtoList(Long memberId) {
        List<MemberAccount> accounts = getMemberAccounts(memberId);

        return accounts.stream().map(account -> {
            int currentPrice = 0; //가져와야되는 값 (WebSocketService)
            return new StockCalculationDTO(account.getStockName(), account.getBuyPrice(), account.getStockCount(), currentPrice);
        }).collect(Collectors.toList());
    }

    public List<MemberAccount> getMemberAccounts(Long memberId) {
        List<MemberAccount> accounts = accountRepository.findByMemberId(memberId)
                .orElseThrow(RuntimeException::new);
        return accounts;
    }

    public StockCalculationDTO getStockCalculationDTOList(Long memberId, String stockName) {
        MemberAccount account = getMemberAccount(memberId, stockName);

        int currentPrice = 0; //가져와야 되는 값( WebSocketService)
        return new StockCalculationDTO(account.getStockName(), account.getBuyPrice(), account.getStockCount(), currentPrice);
    }

    public MemberAccount getMemberAccount(Long memberId, String stockName) {
        return accountRepository.findByMemberIdAndStockName(memberId, stockName)
                .orElseThrow(RuntimeException::new);
    }
}
