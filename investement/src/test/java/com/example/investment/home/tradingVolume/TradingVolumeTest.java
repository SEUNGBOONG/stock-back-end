package com.example.investment.home.tradingVolume;

import com.example.investment.home.tradingVolume.controller.dto.TradingVolumeDTO;
import com.example.investment.home.tradingVolume.infrastructure.TradingVolumeParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TradingVolumeTest {

    private TradingVolumeParser tradingVolumeParser;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper= new ObjectMapper();
        tradingVolumeParser= new TradingVolumeParser(objectMapper);
    }

    @Test
    void 거래량_dto_반환_테스트() throws IOException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/tradingVolumeResponse.json")));

        List<TradingVolumeDTO> dtoList = tradingVolumeParser.getTradingVolume(jsonResponse);

        Assertions.assertEquals(2, dtoList.size());

        TradingVolumeDTO dto = dtoList.get(0);
        Assertions.assertEquals("Stock Name 1", dto.stockName());
        Assertions.assertEquals("1", dto.rank());
        Assertions.assertEquals("10000", dto.currentPrice());
        Assertions.assertEquals("1000000", dto.totalVolume());
        Assertions.assertEquals("20000", dto.prevVolume());
        Assertions.assertEquals("10.0", dto.volumeChangeRate());
    }
}
