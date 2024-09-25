package com.example.investment_api.searchHome;

import com.example.investment_api.searchHome.dto.StockDataDTO;
import com.example.investment_api.searchHome.infrastructure.StockDataParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SearchHomeTest {

    private ObjectMapper objectMapper;
    private StockDataParser stockDataParser;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        stockDataParser = new StockDataParser(objectMapper);
    }

    @Test
    void 주식검색_메인페이지_dto_반환값_테스트() throws IOException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/marketCapitalizationResponse.json")));

        List<StockDataDTO> dtoList = stockDataParser.parse(jsonResponse);

        Assertions.assertEquals(2, dtoList.size());

        StockDataDTO dto = dtoList.get(0);
        Assertions.assertEquals("1", dto.rank());
        Assertions.assertEquals("Stock Name 1", dto.stockName());
        Assertions.assertEquals("150000", dto.stockPrice());
        Assertions.assertEquals("-200", dto.prevChangePrice());
        Assertions.assertEquals("-3", dto.prevChangeRate());
        Assertions.assertEquals("5000000000", dto.marketCapitalization());
        Assertions.assertEquals("60000000000", dto.tradingVolume());
    }
}
