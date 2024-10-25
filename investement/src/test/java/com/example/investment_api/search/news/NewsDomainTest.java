//package com.example.investment_api.search.news;
//
//import com.example.investment_api.search.news.controller.dto.NewsResponse;
//import com.example.investment_api.search.news.infrastructure.NewsParser;
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
//public class NewsDomainTest {
//
//    private NewsParser newsParser;
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        newsParser = new NewsParser(objectMapper);
//    }
//
//    @Test
//    void 뉴스_필드_존재_테스트() throws IOException {
//        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/newsResponse.json")));
//
//        List<NewsResponse> dto = newsParser.parseNews(jsonResponse);
//
//        for (int i = 0; i < dto.size(); i++) {
//            Assertions.assertNotNull(dto.get(i).title());
//            Assertions.assertNotNull(dto.get(i).link());
//        }
//    }
//
//    @Test
//    void 뉴스_필드_조회_테스트() throws IOException{
//        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/newsResponse.json")));
//
//        List<NewsResponse> dto = newsParser.parseNews(jsonResponse);
//
//        Assertions.assertEquals("title 1", dto.get(0).title());
//        Assertions.assertEquals("link 1", dto.get(0).link());
//    }
//}
