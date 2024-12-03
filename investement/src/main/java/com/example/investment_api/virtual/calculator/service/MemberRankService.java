package com.example.investment_api.virtual.calculator.service;

import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.virtual.account.controller.dto.AccountStockData;
import com.example.investment_api.virtual.account.service.StockDataTransferService;
import com.example.investment_api.virtual.calculator.domain.AllStockCalculator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberRankService {

    private final MemberJpaRepository memberJpaRepository;
    private final StockDataTransferService stockDataTransferService;
    private final AllStockCalculator allStockCalculator;

    public MemberRankService( MemberJpaRepository memberJpaRepository, StockDataTransferService stockDataTransferService, AllStockCalculator allStockCalculator) {
        this.memberJpaRepository = memberJpaRepository;
        this.stockDataTransferService = stockDataTransferService;
        this.allStockCalculator = allStockCalculator;
    }

    public Map<Long, Integer> calculateMemberRanks() {
        List<Member> members = memberJpaRepository.findAll();
        Map<Long, Double> memberDepositMap = new HashMap<>();
        for (Member member : members) {
            double rate = member.getDeposit();
            memberDepositMap.put(member.getId(), rate);
        }

        List<Map.Entry<Long, Double>> sortedList = memberDepositMap.entrySet()
                .stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .toList();

        Map<Long, Integer> rankMap = new HashMap<>();
        for (int i = 0; i < sortedList.size(); i++) {
            rankMap.put(sortedList.get(i).getKey(), i + 1);
        }

        return rankMap;
    }
}
