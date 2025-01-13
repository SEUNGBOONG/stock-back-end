package com.example.investment_api.home.news.infrastructure;

import com.example.investment_api.home.news.controller.dto.NewsResponse;

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
    public static final String ITEMS = "items";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    private final ObjectMapper objectMapper;

    public NewsParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<NewsResponse> parseNews(String responseBody) throws IOException {
        return setJsonNode(responseBody);
    }

    private List<NewsResponse> setJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode items = objectMapper.readTree(responseBody).path(ITEMS);

        List<NewsResponse> newsList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();

        extractNews(elements, newsList);
        return newsList;
    }

    private void extractNews(final Iterator<JsonNode> elements, final List<NewsResponse> newsList) {
        int count = 0;

        while (isUnderLimit(elements, count)) {
            JsonNode newsItem = elements.next();

            String title = newsItem.path(TITLE).asText().replaceAll("<.*?>", "");
            String link = newsItem.path(LINK).asText();
            newsList.add(new NewsResponse(title, link));

            count++;
        }
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return elements.hasNext() && count < LIST_SIZE;
    }
}
