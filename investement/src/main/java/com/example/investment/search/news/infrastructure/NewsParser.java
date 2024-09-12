package com.example.investment.search.news.infrastructure;

import com.example.investment.search.news.controller.dto.NewsResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class NewsParser {

    private final ObjectMapper objectMapper;

    public NewsParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<NewsResponse> parseNews(String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode items = rootNode.path("items");

        List<NewsResponse> newsList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();
        int count = 0;

        extractNews(elements, count, newsList);

        return newsList;
    }

    private void extractNews(final Iterator<JsonNode> elements, int count, final List<NewsResponse> newsList) {
        while (elements.hasNext() && count < 3) {
            JsonNode newsItem = elements.next();

            String title = newsItem.path("title").asText().replaceAll("<.*?>", "");
            String link = newsItem.path("link").asText();

            newsList.add(new NewsResponse(title, link));
            count++;
        }
    }
}
