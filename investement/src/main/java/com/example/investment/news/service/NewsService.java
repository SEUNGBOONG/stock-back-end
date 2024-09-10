package com.example.investment.news.service;

import com.example.investment.news.controller.dto.NewsResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Value("${naver.api.clientId}")
    private String clientId;

    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    public List<NewsResponse> getNews(String keyword) throws JSONException {
        return fetchNews(keyword);
    }

    private List<NewsResponse> fetchNews(final String keyword) throws JSONException {
        String url = "https://openapi.naver.com/v1/search/news.json?query=" + keyword;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
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
