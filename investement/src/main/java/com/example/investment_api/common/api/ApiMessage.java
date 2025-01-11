package com.example.investment_api.common.api;

public enum ApiMessage {

    DATA_RANK("data_rank"),

    STOCK_PREV("stck_prpr"),
    STOCK_NAME("hts_kor_isnm"),
    PREV_CHANGE_PRICE("prdy_vrss"),
    PREV_CHANGE_RATE("prdy_ctrt"),
    TRADING_VOLUME("acml_vol"),
    MARKET_CAPITAILIZATION("stck_avls"),
    PREV_SIGN("prdy_vrss_sign"),


    INDEX_VALUE("clpr"),
    FLUCTUATION_RATE("fltRt"),

    NAVER_CLIENT_ID("X-Naver-Client-Id"),
    NAVER_CLIENT_SECRET("X-Naver-Client-Secret"),
    NAVER_URL("https://openapi.naver.com/v1/search/news.json?query="),

    TR_ID("tr_id"),
    APP_KEY("appkey"),
    APP_SECRET("appsecret"),
    AUTHORIZATION("Authorization"),
    BEARER("Bearer"),
    CONTENT_TYPE("Content-Type"),
    APPLICATION_JSON("application/json");

    private final String name;

    ApiMessage(final String name) {
        this.name = name;
    }
}
