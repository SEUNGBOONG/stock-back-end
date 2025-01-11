package com.example.investment_api.common.api;

public enum ApiMessage {

    DATA_RANK("data_rank"),

    STOCK_ITEM_NAME("bstp_kor_isnm"),

    STOCK_PREV("stck_prpr"),
    STOCK_NAME("hts_kor_isnm"),
    PREV_CHANGE_PRICE("prdy_vrss"),
    PREV_CHANGE_RATE("prdy_ctrt"),
    TRADING_VOLUME("acml_vol"),
    MARKET_CAPITAILIZATION("stck_avls"),
    PREV_SIGN("prdy_vrss_sign"),
    TOTAL_VOLUME("acml_vol"),
    INDEX_VALUE("clpr"),
    FLUCTUATION_RATE("fltRt"),
    HIGH_STOCK_PRICE("stck_hgpr"),
    LOW_STOCK_PRICE("stck_lwpr"),
    PREV_VOLUME("prdy_vol"),
    VOLUME_CHANGE_RATE("vol_inrt"),

    NAVER_CLIENT_ID("X-Naver-Client-Id"),
    NAVER_CLIENT_SECRET("X-Naver-Client-Secret"),
    NAVER_URL("https://openapi.naver.com/v1/search/news.json?query="),

    TR_ID("tr_id"),
    APP_KEY("appkey"),
    APP_SECRET("appsecret"),
    AUTHORIZATION("Authorization"),
    BEARER("Bearer "),
    CONTENT_TYPE("Content-Type"),
    APPLICATION_JSON("application/json");

    private final String name;

    ApiMessage(final String name) {
        this.name = name;
    }
}
