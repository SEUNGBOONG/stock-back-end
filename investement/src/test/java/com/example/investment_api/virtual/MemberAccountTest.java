package com.example.investment_api.virtual;

import com.example.investment_api.virtual.account.MemberAccount;
import com.example.investment_api.virtual.account.MemberAccountRepository;
import com.example.investment_api.virtual.account.MemberAccountService;
import com.example.investment_api.virtual.calculator.dto.StockCalculationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class MemberAccountTest {
    @Mock
    private MemberAccountRepository accountRepository;

    @InjectMocks
    private MemberAccountService memberAccountService;

    private MemberAccount testAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testAccount = new MemberAccount(1L, "TestStock", 1000, 10);
    }

    @Test
    void DTO_생성_테스트() {
        Long memberId = 1L;
        String stockName = "TestStock";

        when(accountRepository.findByMemberIdAndStockName(memberId, stockName))
                .thenReturn(Optional.of(testAccount));

        StockCalculationDTO result = memberAccountService.getStockCalculationDTOList(memberId, stockName);

        assertNotNull(result);
        assertAll(
                () -> assertEquals("TestStock", result.stockName()),
                () -> assertEquals(1000, result.buyPrice()),
                () -> assertEquals(10, result.stockCount()),
                () -> assertEquals(0, result.currentPrice())
        );
    }

    @Test
    void 아이디와_주식명으로_찾기() {
        Long memberId = 1L;
        String stockName = "TestStock";

        when(accountRepository.findByMemberIdAndStockName(memberId, stockName))
                .thenReturn(Optional.of(testAccount));

        MemberAccount result = memberAccountService.getMemberAccount(memberId, stockName);

        assertNotNull(result);
        assertAll(
                () -> assertEquals(memberId, result.getMemberId()),
                () -> assertEquals(stockName, result.getStockName())
        );
    }

    @Test
    void 멤버_아이디로_주식_찾기() {
        Long memberId = 1L;
        List<MemberAccount> accounts = Arrays.asList(testAccount);

        when(accountRepository.findByMemberId(memberId)).thenReturn(Optional.of(accounts));

        List<MemberAccount> result = memberAccountService.getMemberAccounts(memberId);

        assertNotNull(result);
        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals("TestStock", result.get(0).getStockName())
        );
    }
}
