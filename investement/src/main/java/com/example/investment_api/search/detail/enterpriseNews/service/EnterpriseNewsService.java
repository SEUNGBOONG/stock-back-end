package com.example.investment_api.search.detail.enterpriseNews.service;

import com.example.investment_api.home.news.service.client.NewsFetcher;
import com.example.investment_api.search.detail.enterpriseNews.controller.dto.NewsDTO;
import com.example.investment_api.search.detail.enterpriseNews.infrastructure.EnterpriseNewsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EnterpriseNewsService {

    private final NewsFetcher newsFetcher;
    private final EnterpriseNewsParser enterpriseNewsParser;

    @Autowired
    public EnterpriseNewsService(final NewsFetcher newsFetcher, final EnterpriseNewsParser enterpriseNewsParser) {
        this.newsFetcher = newsFetcher;
        this.enterpriseNewsParser= enterpriseNewsParser;
    }

    public List<NewsDTO> getNews(String keyword) throws IOException {
        return getNewsResponses(keyword);
    }

    private List<NewsDTO> getNewsResponses(final String keyword) throws IOException {
        String response = String.valueOf(newsFetcher.fetch(keyword));
        return enterpriseNewsParser.parseNews(response);
    }
}
