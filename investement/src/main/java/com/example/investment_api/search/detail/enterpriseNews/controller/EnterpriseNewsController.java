package com.example.investment_api.search.detail.enterpriseNews.controller;

import com.example.investment_api.search.detail.enterpriseNews.controller.dto.NewsDTO;
import com.example.investment_api.search.detail.enterpriseNews.service.EnterpriseNewsService;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search/news")
public class EnterpriseNewsController {

    private final EnterpriseNewsService enterpriseNewsService;

    public EnterpriseNewsController(final EnterpriseNewsService enterpriseNewsService) {
        this.enterpriseNewsService = enterpriseNewsService;
    }

    @GetMapping
    public List<NewsDTO> getNews(@RequestParam String stockName) throws JSONException, IOException {
        return enterpriseNewsService.getNews(stockName);
    }
}
