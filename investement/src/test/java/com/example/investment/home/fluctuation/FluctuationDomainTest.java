package com.example.investment.home.fluctuation;

import com.example.investment.home.fluctuation.controller.dto.response.FluctuationDTO;
import com.example.investment.home.fluctuation.infrastructor.FluctuationParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FluctuationDomainTest {
    private FluctuationParser fluctuationParser;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        fluctuationParser = new FluctuationParser(objectMapper);
    }

    @Test
    void 등락률_dto_반환값_테스트() throws IOException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/fluctuationResponse.json")));

        List<FluctuationDTO> dtoList = fluctuationParser.getFluctuation(jsonResponse);

        Assertions.assertEquals(2, dtoList.size());

        FluctuationDTO dto = dtoList.get(0);
        Assertions.assertEquals("Stock Name 1", dto.stockName());
        Assertions.assertEquals(1, dto.rank());
        Assertions.assertEquals(1000, dto.currentPrice());
        Assertions.assertEquals(500, dto.prevChangePrice());
        Assertions.assertEquals("positive", dto.prevSign());
        Assertions.assertEquals(10.5, dto.prevChangeRate());
    }
}
