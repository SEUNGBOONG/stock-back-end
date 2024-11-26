package com.example.investment_api.search.detail.chart.domain;

import com.example.investment_api.search.detail.chart.controller.dto.ChartDTO;
import com.example.investment_api.search.detail.chart.controller.dto.ChartDTOs;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChartParser {

    private final ObjectMapper objectMapper;

    public ChartParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ChartDTOs parseChartData(String responseBody) throws IOException {
        return getDayChartDTO(responseBody);
    }

    private ChartDTOs getDayChartDTO(final String responseBody) throws IOException {
        List<JsonNode> items = getJsonNodeElements(responseBody);
        return new ChartDTOs(extractChartData(items));
    }

    private List<JsonNode> getJsonNodeElements(final String responseBody) throws IOException {
        JsonNode itemsNode = objectMapper.readTree(responseBody).path("output2");
        List<JsonNode> itemsList = new ArrayList<>();

        for (int i = 0; i < itemsNode.size(); i++) {
            itemsList.add(itemsNode.get(i));
        }
        return itemsList;
    }

    private List<ChartDTO> extractChartData(final List<JsonNode> items) {
        List<ChartDTO> chartDtos = new ArrayList<>();

        for (JsonNode item : items) {
            ChartDTO dto = new ChartDTO(
                    item.get("stck_bsop_date").asText(),
                    item.get("stck_clpr").asText(),
                    item.get("stck_hgpr").asText(),
                    item.get("stck_lwpr").asText(),
                    item.get("prdy_vrss").asText()
            );
            chartDtos.add(dto);
        }

        return chartDtos;
    }
}
