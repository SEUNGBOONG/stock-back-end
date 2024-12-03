package com.example.investment_api.portfolio.service;

import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.exception.exceptions.auth.NotFoundMemberByEmailException;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.portfolio.controller.dto.PortfolioResponse;
import com.example.investment_api.portfolio.infrastructure.PortfolioType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {

    private final MemberJpaRepository memberJpaRepository;

    public PortfolioService(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    public List<PortfolioResponse> getRecommendedPortfolio(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(NotFoundMemberByEmailException::new);
        int annualIncome = member.getAnnualIncome();
        List<PortfolioResponse> portfolios = new ArrayList<>();
        if (annualIncome >= 20000000 && annualIncome < 30000000) {
            portfolios.add(new PortfolioResponse(50, 20, 20, 10, PortfolioType.RANGE_2000_3000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(30, 30, 30, 10, PortfolioType.RANGE_2000_3000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(40, 25, 25, 10, PortfolioType.RANGE_2000_3000_NORMAL.getDescription()));
        } else if (annualIncome >= 30000000 && annualIncome < 40000000) {
            portfolios.add(new PortfolioResponse(55, 20, 15, 10, PortfolioType.RANGE_3000_4000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(25, 30, 35, 10, PortfolioType.RANGE_3000_4000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(35, 30, 25, 10, PortfolioType.RANGE_3000_4000_NORMAL.getDescription()));
        } else if (annualIncome >= 40000000 && annualIncome < 50000000) {
            portfolios.add(new PortfolioResponse(60, 15, 15, 10, PortfolioType.RANGE_4000_5000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(40, 30, 20, 10, PortfolioType.RANGE_4000_5000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(50, 20, 20, 10, PortfolioType.RANGE_4000_5000_NORMAL.getDescription()));
        } else if (annualIncome >= 50000000 && annualIncome < 60000000) {
            portfolios.add(new PortfolioResponse(65, 15, 10, 10, PortfolioType.RANGE_5000_6000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(35, 25, 30, 10, PortfolioType.RANGE_5000_6000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(45, 25, 20, 10, PortfolioType.RANGE_5000_6000_NORMAL.getDescription()));
        } else if (annualIncome >= 60000000 && annualIncome < 70000000) {
            portfolios.add(new PortfolioResponse(70, 20, 10, 10, PortfolioType.RANGE_6000_7000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(40, 30, 20, 10, PortfolioType.RANGE_6000_7000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(50, 25, 15, 10, PortfolioType.RANGE_6000_7000_NORMAL.getDescription()));
        } else if (annualIncome >= 70000000 && annualIncome < 80000000) {
            portfolios.add(new PortfolioResponse(75, 10, 5, 10, PortfolioType.RANGE_7000_8000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(45, 20, 25, 10, PortfolioType.RANGE_7000_8000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(60, 15, 15, 10, PortfolioType.RANGE_7000_8000_NORMAL.getDescription()));
        } else if (annualIncome >= 80000000 && annualIncome < 90000000) {
            portfolios.add(new PortfolioResponse(80, 5, 5, 10, PortfolioType.RANGE_8000_9000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(50, 20, 20, 10, PortfolioType.RANGE_8000_9000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(70, 10, 10, 10, PortfolioType.RANGE_8000_9000_NORMAL.getDescription()));
        } else if (annualIncome >= 90000000 && annualIncome < 100000000) {
            portfolios.add(new PortfolioResponse(85, 5, 5, 5, PortfolioType.RANGE_9000_10000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(55, 15, 15, 15, PortfolioType.RANGE_9000_10000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(70, 15, 10, 5, PortfolioType.RANGE_9000_10000_NORMAL.getDescription()));
        } else {
            portfolios.add(new PortfolioResponse(0, 0, 0, 0, PortfolioType.Default.getDescription()));
        }

        return portfolios;
    }
}
