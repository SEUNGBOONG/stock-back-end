package com.example.investment_api.home.fluctuation.controller.dto.response;

public record FluctuationDTO(
        String stockName, //HTS 한글 종목명
        int rank, //데이터(등락률) 순위
        int currentPrice, //현재가
        int prevChangePrice, //전일 대비가
        String prevSign, //전일 대비 부호
        Double prevChangeRate //전일 대비율
) {
}

