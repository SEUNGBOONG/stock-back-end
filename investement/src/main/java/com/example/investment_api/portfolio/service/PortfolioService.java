package com.example.investment_api.portfolio.service;

import com.example.investment_api.member.domain.member.Member;

import com.example.investment_api.member.exception.exceptions.auth.NotFoundMemberByEmailException;

import com.example.investment_api.member.infrastructure.member.MemberJpaRepository;

import com.example.investment_api.portfolio.controller.dto.PortfolioRequest;
import com.example.investment_api.portfolio.controller.dto.PortfolioResponse;

import com.example.investment_api.portfolio.infrastructure.PortfolioType;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private final MemberJpaRepository memberJpaRepository;

    public PortfolioService(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    public PortfolioResponse getRecommendedPortfolio(PortfolioRequest portfolioRequest) {
        Member member = memberJpaRepository.findById(portfolioRequest.memberId())
                .orElseThrow(NotFoundMemberByEmailException::new);

        int annualIncome = member.getAnnualIncome();
        boolean propensity = member.isPropensity();

        if (annualIncome >= 20000000 && annualIncome < 30000000) {
            if (propensity) {
                return new PortfolioResponse(50, 20, 20, 10, PortfolioType.RANGE_2000_3000_AGGRESSIVE.getDescription());
            } else {
                return new PortfolioResponse(20, 30, 40, 10, PortfolioType.RANGE_2000_3000_CONSERVATIVE.getDescription());
            }
        }
        if (annualIncome >= 30000000 && annualIncome < 40000000) {
            if (propensity) {
                return new PortfolioResponse(55, 20, 15, 10, PortfolioType.RANGE_3000_4000_AGGRESSIVE.getDescription());
            } else {
                return new PortfolioResponse(25, 30, 35, 10, PortfolioType.RANGE_3000_4000_CONSERVATIVE.getDescription());
            }
        }
        if (annualIncome >= 40000000 && annualIncome < 50000000) {
            if (propensity) {
                return new PortfolioResponse(60, 15, 15, 10, PortfolioType.RANGE_4000_5000_AGGRESSIVE.getDescription());
            } else {
                return new PortfolioResponse(60, 15, 10, 10, PortfolioType.RANGE_4000_5000_CONSERVATIVE.getDescription());
            }
        }
        if (annualIncome >= 50000000 && annualIncome < 60000000) {
            if (propensity) {
                return new PortfolioResponse(65, 15, 10, 10, PortfolioType.RANGE_5000_6000_AGGRESSIVE.getDescription());
            } else {
                return new PortfolioResponse(35, 25, 30, 10, PortfolioType.RANGE_5000_6000_CONSERVATIVE.getDescription());
            }
        }
        if (annualIncome >= 60000000 && annualIncome < 70000000) {
            if (propensity) {
                return new PortfolioResponse(70, 20, 30, 10, PortfolioType.RANGE_6000_7000_AGGRESSIVE.getDescription());
            } else {
                return new PortfolioResponse(40, 20, 30, 10, PortfolioType.RANGE_6000_7000_CONSERVATIVE.getDescription());
            }
        }
        if (annualIncome >= 70000000 && annualIncome < 80000000) {
            if (propensity) {
                return new PortfolioResponse(75, 10, 5, 10, PortfolioType.RANGE_7000_8000_AGGRESSIVE.getDescription());
            } else {
                return new PortfolioResponse(45, 20, 25, 10, PortfolioType.RANGE_7000_8000_CONSERVATIVE.getDescription());
            }
        }
        if (annualIncome >= 80000000 && annualIncome < 90000000) {
            if (propensity) {
                return new PortfolioResponse(80, 5, 5, 10, PortfolioType.RANGE_8000_9000_AGGRESSIVE.getDescription());
            } else {
                return new PortfolioResponse(50, 20, 20, 10, PortfolioType.RANGE_8000_9000_CONSERVATIVE.getDescription());
            }
        }
        if (annualIncome >= 90000000 && annualIncome < 100000000) {
            if (propensity) {
                return new PortfolioResponse(85, 5, 5, 5, PortfolioType.RANGE_9000_10000_AGGRESSIVE.getDescription());
            } else {
                return new PortfolioResponse(55, 15, 15, 15, PortfolioType.RANGE_9000_10000_CONSERVATIVE.getDescription());
            }
        }
        return new PortfolioResponse(0, 0, 0, 0, PortfolioType.Default.getDescription());
    }
}
