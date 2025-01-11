package com.example.investment_api.common.api;

public enum KoreaInvestement {

    DATA_RANK("data_rank"),
    STOCK_PREV("stck_prpr"),
    STOCK_NAME("hts_kor_isnm"),
    PREV_CHANGE_PRICE("prdy_vrss"),
    PREV_CHANGE_RATE("prdy_ctrt"),
    TRADING_VOLUME("acml_vol"),
    MARKET_CAPITAILIZATION("stck_avls");
    private final String name;

    KoreaInvestement(final String name) {
        this.name = name;
    }
}
