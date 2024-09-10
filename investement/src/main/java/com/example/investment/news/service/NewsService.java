package com.example.investment.news.service;

import com.example.investment.news.controller.dto.NewsResponse;

import com.example.investment.news.service.client.NewsFetcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    private final NewsFetcher newsFetcher;

    public NewsService(NewsFetcher newsFetcher) {
        this.newsFetcher = newsFetcher;
    }

    public List<NewsResponse> getNews(String keyword) throws JSONException {
        return fetchNews(keyword);
    }

    private List<NewsResponse> fetchNews(final String keyword) throws JSONException {
        ResponseEntity<String> response = newsFetcher.fetch(keyword);
        return parseNews(response);
    }

    private static List<NewsResponse> parseNews(final ResponseEntity<String> response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray items = jsonObject.getJSONArray("items");

        List<NewsResponse> newsList = new ArrayList<>();
        for (int i = 0; i < Math.min(3, items.length()); i++) {
            JSONObject newsItem = items.getJSONObject(i);
            String title = newsItem.getString("title").replaceAll("<.*?>", "");
            String link = newsItem.getString("link");
            newsList.add(new NewsResponse(title, link));
        }
        return newsList;
    }
}
