//package com.example.investment_api.home.marketCapitalization;
//
//import com.example.investment_api.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;
//import com.example.investment_api.home.marketCapitalization.infrastructure.MarketCapitalizationParser;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class MarketCapitalizationDomainTest {
//
//    private MarketCapitalizationParser marketCapitalizationParser;
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        marketCapitalizationParser = new MarketCapitalizationParser(objectMapper);
//    }
//
//    @Test
//    void 시가총액_dto_반환값_테스트() throws IOException {
//        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/marketCapitalizationResponse.json")));
//
//        List<MarketCapitalizationDTO> dtoList = marketCapitalizationParser.parse(jsonResponse);
//
//        Assertions.assertEquals(2, dtoList.size());
//
//        MarketCapitalizationDTO dto = dtoList.get(0);
//        Assertions.assertEquals("1", dto.rank());
//        Assertions.assertEquals("150000", dto.stockPrice());
//        Assertions.assertEquals("Stock Name 1", dto.stockName());
//        Assertions.assertEquals("5000000000", dto.marketCapitalization());
//    }
//}
