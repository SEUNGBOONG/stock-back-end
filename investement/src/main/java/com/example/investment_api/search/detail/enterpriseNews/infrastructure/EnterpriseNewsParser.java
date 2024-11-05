package com.example.investment_api.search.detail.enterpriseNews.infrastructure;

import com.example.investment_api.search.detail.enterpriseNews.controller.dto.NewsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class EnterpriseNewsParser {

    private static final int LIST_SIZE = 10;
    private final ObjectMapper objectMapper;

    public EnterpriseNewsParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<NewsDTO> parseNews(String responseBody) throws IOException {
        return setJsonNode(responseBody);
    }

    private List<NewsDTO> setJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode items = objectMapper.readTree(responseBody).path("items");

        List<NewsDTO> newsList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();

        extractNews(elements, newsList);
        return newsList;
    }

    private void extractNews(final Iterator<JsonNode> elements, final List<NewsDTO> newsList) {
        int count = 0;

        while (isUnderLimit(elements, count)) {
            JsonNode newsItem = elements.next();

            String title = newsItem.path("title").asText().replaceAll("<.*?>", "");
            String link = newsItem.path("link").asText();
            String pubDate= newsItem.path("pubDate").asText();
            newsList.add(new NewsDTO(title, link, pubDate));

            count++;
        }
    }

    private boolean isUnderLimit(Iterator<JsonNode> elements, int count) {
        return elements.hasNext() && count < LIST_SIZE;
    }
}
