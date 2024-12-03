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
        Map<Long, Double> memberProfitMap = new HashMap<>();
        for (Member member : members) {
            List<AccountStockData> stockDataList = stockDataTransferService.getAccountStockDataList(member.getId());
            double totalProfit = allStockCalculator.calculateTotalProfit(stockDataList);
            memberProfitMap.put(member.getId(), totalProfit);
        }

        List<Map.Entry<Long, Double>> sortedList = memberProfitMap.entrySet()
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
