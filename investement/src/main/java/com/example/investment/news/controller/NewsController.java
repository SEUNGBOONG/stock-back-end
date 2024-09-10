package com.example.investment.news.controller;

import com.example.investment.news.controller.dto.NewsResponse;

import com.example.investment.news.service.NewsService;

import org.json.JSONException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(final NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<NewsResponse> getNews(@RequestParam String keyword) throws JSONException {
        return newsService.getNews(keyword);
    }
}
