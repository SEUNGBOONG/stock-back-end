package com.example.investment.news.service;

import com.example.investment.news.controller.dto.NewsResponse;
import com.example.investment.news.service.client.NewsFetcher;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Service
public class NewsService {

    private final NewsFetcher newsFetcher;
    private final ObjectMapper objectMapper;

    public NewsService(NewsFetcher newsFetcher, ObjectMapper objectMapper) {
        this.newsFetcher = newsFetcher;
        this.objectMapper = objectMapper;
    }

    public List<NewsResponse> getNews(String keyword) throws IOException {
        ResponseEntity<String> response = newsFetcher.fetch(keyword);
        return parseNews(response);
    }

    private List<NewsResponse> parseNews(final ResponseEntity<String> response) throws IOException {
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode items = rootNode.path("items");

        List<NewsResponse> newsList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        int count = 0;

        while (elements.hasNext() && count < 3) {
            JsonNode newsItem = elements.next();
            String title = newsItem.path("title").asText().replaceAll("<.*?>", "");
            String link = newsItem.path("link").asText();
            newsList.add(new NewsResponse(title, link));
            count++;
        }

        return newsList;
    }
}
