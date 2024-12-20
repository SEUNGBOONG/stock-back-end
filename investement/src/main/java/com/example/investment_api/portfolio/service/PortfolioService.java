package com.example.investment_api.portfolio.service;

import com.example.investment_api.member.domain.member.Member;
import com.example.investment_api.member.exception.exceptions.auth.NotFoundMemberByEmailException;
import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;
import com.example.investment_api.portfolio.controller.dto.PortfolioResponse;
import com.example.investment_api.portfolio.infrastructure.PortfolioDescription;
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
            portfolios.add(new PortfolioResponse(PortfolioType.AGGRESSIVE.name(), 50, 20, 20, 10, PortfolioDescription.RANGE_2000_3000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.PASSIVE.name(), 30, 30, 30, 10, PortfolioDescription.RANGE_2000_3000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.NORMAL.name(), 40, 25, 25, 10, PortfolioDescription.RANGE_2000_3000_NORMAL.getDescription()));
        } else if (annualIncome >= 30000000 && annualIncome < 40000000) {
            portfolios.add(new PortfolioResponse(PortfolioType.AGGRESSIVE.name(),55, 20, 15, 10, PortfolioDescription.RANGE_3000_4000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.PASSIVE.name(),25, 30, 35, 10, PortfolioDescription.RANGE_3000_4000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.NORMAL.name(),35, 30, 25, 10, PortfolioDescription.RANGE_3000_4000_NORMAL.getDescription()));
        } else if (annualIncome >= 40000000 && annualIncome < 50000000) {
            portfolios.add(new PortfolioResponse(PortfolioType.AGGRESSIVE.name(),60, 15, 15, 10, PortfolioDescription.RANGE_4000_5000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.PASSIVE.name(),40, 30, 20, 10, PortfolioDescription.RANGE_4000_5000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.NORMAL.name(),50, 20, 20, 10, PortfolioDescription.RANGE_4000_5000_NORMAL.getDescription()));
        } else if (annualIncome >= 50000000 && annualIncome < 60000000) {
            portfolios.add(new PortfolioResponse(PortfolioType.AGGRESSIVE.name(),65, 15, 10, 10, PortfolioDescription.RANGE_5000_6000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.PASSIVE.name(),35, 25, 30, 10, PortfolioDescription.RANGE_5000_6000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.NORMAL.name(),45, 25, 20, 10, PortfolioDescription.RANGE_5000_6000_NORMAL.getDescription()));
        } else if (annualIncome >= 60000000 && annualIncome < 70000000) {
            portfolios.add(new PortfolioResponse(PortfolioType.AGGRESSIVE.name(),70, 20, 10, 10, PortfolioDescription.RANGE_6000_7000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.PASSIVE.name(),40, 30, 20, 10, PortfolioDescription.RANGE_6000_7000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.NORMAL.name(),50, 25, 15, 10, PortfolioDescription.RANGE_6000_7000_NORMAL.getDescription()));
        } else if (annualIncome >= 70000000 && annualIncome < 80000000) {
            portfolios.add(new PortfolioResponse(PortfolioType.AGGRESSIVE.name(),75, 10, 5, 10, PortfolioDescription.RANGE_7000_8000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.PASSIVE.name(),45, 20, 25, 10, PortfolioDescription.RANGE_7000_8000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.NORMAL.name(),60, 15, 15, 10, PortfolioDescription.RANGE_7000_8000_NORMAL.getDescription()));
        } else if (annualIncome >= 80000000 && annualIncome < 90000000) {
            portfolios.add(new PortfolioResponse(PortfolioType.AGGRESSIVE.name(),80, 5, 5, 10, PortfolioDescription.RANGE_8000_9000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.PASSIVE.name(),50, 20, 20, 10, PortfolioDescription.RANGE_8000_9000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.NORMAL.name(),70, 10, 10, 10, PortfolioDescription.RANGE_8000_9000_NORMAL.getDescription()));
        } else if (annualIncome >= 90000000 && annualIncome < 100000000) {
            portfolios.add(new PortfolioResponse(PortfolioType.AGGRESSIVE.name(),85, 5, 5, 5, PortfolioDescription.RANGE_9000_10000_AGGRESSIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.PASSIVE.name(),55, 15, 15, 15, PortfolioDescription.RANGE_9000_10000_CONSERVATIVE.getDescription()));
            portfolios.add(new PortfolioResponse(PortfolioType.NORMAL.name(),70, 15, 10, 5, PortfolioDescription.RANGE_9000_10000_NORMAL.getDescription()));
        } else {
            portfolios.add(new PortfolioResponse(PortfolioType.NONE.name(), 0, 0, 0, 0, PortfolioDescription.Default.getDescription()));
        }

        return portfolios;
    }
}
