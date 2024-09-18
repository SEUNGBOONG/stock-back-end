package com.example.investment.search.news.service;

import com.example.investment.search.news.controller.dto.NewsResponse;

import com.example.investment.search.news.infrastructure.NewsParser;

import com.example.investment.search.news.service.client.NewsFetcher;

import jakarta.transaction.Transactional;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class NewsService {

    private final NewsFetcher newsFetcher;
    private final NewsParser newsParser;

    public NewsService(final NewsFetcher newsFetcher, final NewsParser newsParser) {
        this.newsFetcher = newsFetcher;
        this.newsParser = newsParser;
    }

    public List<NewsResponse> getNews(String keyword) throws IOException {
        return getNewsResponses(keyword);
    }

    private List<NewsResponse> getNewsResponses(final String keyword) throws IOException {
        ResponseEntity<String> response = newsFetcher.fetch(keyword);
        return newsParser.parseNews(response.getBody());
    }
}
