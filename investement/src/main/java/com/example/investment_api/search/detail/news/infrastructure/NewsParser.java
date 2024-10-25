package com.example.investment_api.search.detail.news.infrastructure;

import com.example.investment_api.search.detail.news.controller.dto.NewsResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class NewsParser {

    private static final int LIST_SIZE = 3;
    private final ObjectMapper objectMapper;

    public NewsParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<NewsResponse> parseNews(String responseBody) throws IOException {
        return setJsonNode(responseBody);
    }

    private List<NewsResponse> setJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode items = objectMapper.readTree(responseBody).path("items");

        List<NewsResponse> newsList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();

        extractNews(elements, newsList);
        return newsList;
    }

    private void extractNews(final Iterator<JsonNode> elements, final List<NewsResponse> newsList) {
        int count = 0;

        while (isUnderLimit(elements, count)) {
            JsonNode newsItem = elements.next();

            String title = newsItem.path("title").asText().replaceAll("<.*?>", "");
            String link = newsItem.path("link").asText();
            newsList.add(new NewsResponse(title, link));

            count++;
        }
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return elements.hasNext() && count < LIST_SIZE;
    }
}
