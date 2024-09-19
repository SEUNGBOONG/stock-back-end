package com.example.investment_api.search.consensus.domain;

import com.example.investment_api.search.consensus.controller.dto.InvestmentRecommendationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ConsensusDomainTest {

    private InvestmentRecommendationParser parser;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper= new ObjectMapper();
        parser= new InvestmentRecommendationParser(objectMapper);
    }

    @Test
    void 컨센서스_dto_반환값_테스트 () throws IOException {
        String jsonResponse= new String(Files.readAllBytes(Paths.get("src/test/resources/InvestmentRecommendationResponse.json")));

        InvestmentRecommendationDTO dto = parser.parseInvestmentRecommendation(jsonResponse);

        Assertions.assertEquals("95000.00", dto.avgTargetPrice());
        Assertions.assertEquals("77500.00", dto.avgStockPrice());
        Assertions.assertEquals("-22500.00", dto.avgStockDifferencePrice());
    }
}
