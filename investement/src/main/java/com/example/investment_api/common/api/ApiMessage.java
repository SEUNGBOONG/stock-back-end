package com.example.investment_api.common.api;

import lombok.Getter;

@Getter
public enum ApiMessage {
    TR_ID("tr_id"),
    INDEX_NAME("idxNm"),
    INDEX_VALUE("clpr"),
    FLUCTUATION_RATE("fltRt")
    ;

    ApiMessage(final String message) {
        this.message = message;
    }

    private String message;

}
