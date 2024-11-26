package com.example.investment_api.search.detail.chart.domain;

import com.example.investment_api.search.detail.chart.controller.dto.ChartDTO;
import com.example.investment_api.search.detail.chart.controller.dto.ChartDTOs;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

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
        Iterator<JsonNode> elements = getJsonNodeElements(responseBody);
        return new ChartDTOs(extractChartData(elements));
    }

    private Iterator<JsonNode> getJsonNodeElements(final String responseBody) throws IOException {
        JsonNode items = objectMapper.readTree(responseBody).path("output2");
        return items.elements();
    }

    private List<ChartDTO> extractChartData(final Iterator<JsonNode> elements) {
        List<ChartDTO> chartDtos = new ArrayList<>();

        for (JsonNode item : elements.next()) {
            ChartDTO dto = new ChartDTO(
                    item.path("stck_bsop_date").asText(),
                    item.path("stck_clpr").asText(),
                    item.path("stck_hgpr").asText(),
                    item.path("stck_lwpr").asText(),
                    item.path("prdy_vrss").asText()
            );
            System.out.println(item);

            chartDtos.add(dto);
        }

        return chartDtos;
    }
}
