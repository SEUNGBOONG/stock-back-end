package com.example.investment_api.virtual.calculator.infrastructure;

import com.example.investment_api.virtual.account.dto.AccountDataDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDataParser {

    private final ObjectMapper objectMapper;

    public AccountDataParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /* public AccountDataDTO parse(String responseBody, String targetStockName) throws IOException {
        return getTargetStockData(responseBody, targetStockName);
    }

     */

    public List<AccountDataDTO> parseAll(String responseBody) throws IOException{
        JsonNode items = getJsonNode(responseBody);
        List<AccountDataDTO> stockDataList = new ArrayList<>();

        for (JsonNode item : items) {
            AccountDataDTO stockData = extractStockData(item);
            stockDataList.add(stockData);
        }

        return stockDataList;
    }

    private AccountDataDTO extractStockData(final JsonNode item) {
        String stockName = item.path("hts_kor_isnm").asText();  // 주식 이름
        int stockPrice = item.path("stck_prpr").asInt();        // 현재 주가
        double prevChangeRate = item.path("prdy_ctrt").asDouble(); // 전일 대비 등락률

        return new AccountDataDTO(stockName, stockPrice, prevChangeRate);
    }

    private JsonNode getJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path("output");
    }

    /*private AccountDataDTO getTargetStockData(final String responseBody, String targetStockName) throws JsonProcessingException {
        JsonNode items = getJsonNode(responseBody);
        for (JsonNode item : items) {
            String stockName = item.path("hts_kor_isnm").asText();
            if (targetStockName.equals(stockName)) {
                return extractStockData(item);
            }
        }
        return null;
    }

    private AccountDataDTO extractStockData(final JsonNode item) {
        int stockPrice = item.path("stck_prpr").asInt();
        Double prevChangeRate = item.path("prdy_ctrt").asDouble();

        return new AccountDataDTO(stockPrice, prevChangeRate);
    }

    private JsonNode getJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        return rootNode.path("output");
    }

     */
}
