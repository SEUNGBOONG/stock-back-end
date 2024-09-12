package com.example.investment.search.searchStocks.service;

import com.example.investment.search.searchStocks.controller.dto.SearchStocksDTO;
import com.example.investment.search.searchStocks.service.client.SearchStocksDataFetcher;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Service
@Transactional
public class SearchStocksService {

    private final SearchStocksDataFetcher searchStocksDataFetcher;


    public SearchStocksService(final SearchStocksDataFetcher searchStocksDataFetcher) {
        this.searchStocksDataFetcher = searchStocksDataFetcher;
    }

    public List<SearchStocksDTO> getFinancialRatio(String fidInputIscd) {
        JSONObject jsonObject = searchStocksDataFetcher.SearchStocksData(fidInputIscd);
        JSONArray outputArray = jsonObject.getJSONArray("output");
        return null;
    }
}
