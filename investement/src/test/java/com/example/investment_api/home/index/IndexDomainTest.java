//package com.example.investment_api.home.index;
//
//import com.example.investment_api.home.index.controller.dto.KOSDAQResponse;
//import com.example.investment_api.home.index.controller.dto.KOSPIResponse;
//
//import com.example.investment_api.home.index.infrastructure.IndexParser;
//
//import org.json.JSONObject;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//public class IndexDomainTest {
//
//    private IndexParser indexParser;
//
//    @BeforeEach
//    void setUp() {
//        indexParser = new IndexParser();
//    }
//
//    @Test
//    void KOSPI_응답_파싱_테스트() throws IOException {
//        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/kospiResponse.json")));
//        JSONObject jsonObject = new JSONObject(jsonResponse);
//
//        KOSPIResponse response = indexParser.parseKOSPIResponse(jsonObject);
//
//        Assertions.assertEquals("KOSPI", response.indexName());
//        Assertions.assertEquals("3000", response.indexValue());
//        Assertions.assertEquals("-0.5", response.fluctuationRate());
//    }
//
//    @Test
//    void KOSDAQ_응답_파싱_테스트() throws IOException {
//        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/kosdaqResponse.json")));
//        JSONObject jsonObject = new JSONObject(jsonResponse);
//
//        KOSDAQResponse response = indexParser.parseKOSDAQResponse(jsonObject);
//
//        Assertions.assertEquals("KOSDAQ", response.indexName());
//        Assertions.assertEquals("900", response.indexValue());
//        Assertions.assertEquals("0.3", response.fluctuationRate());
//    }
//}
