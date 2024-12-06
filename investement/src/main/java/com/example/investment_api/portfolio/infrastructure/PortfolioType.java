package com.example.investment_api.portfolio.infrastructure;

public enum PortfolioType {

    NORMAL("보통"),
    PASSIVE("소극적"),
    AGGRESSIVE("공격적"),
    NONE("저연봉자");

    private final String name;

    PortfolioType(String name) {
        this.name = name;
    }
}
