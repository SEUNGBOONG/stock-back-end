package com.example.investment_api.home.news.service;

import com.example.investment_api.home.news.infrastructure.NewsParser;
import com.example.investment_api.home.news.service.client.NewsFetcher;
import com.example.investment_api.home.news.controller.dto.NewsResponse;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class NewsService {

    private final NewsFetcher newsFetcher;
    private final NewsParser newsParser;

    @Autowired
    public NewsService(final NewsFetcher newsFetcher, final NewsParser newsParser) {
        this.newsFetcher = newsFetcher;
        this.newsParser = newsParser;
    }

    public List<NewsResponse> getNewsResponses() throws IOException {
        ResponseEntity<String> response = newsFetcher.fetch("경제뉴스");
        return newsParser.parseNews(response.getBody());
    }
}
