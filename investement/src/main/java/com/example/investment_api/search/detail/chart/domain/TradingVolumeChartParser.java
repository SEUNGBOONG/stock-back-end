package com.example.investment_api.search.detail.chart.domain;

import com.example.investment_api.search.detail.chart.controller.dto.TradingVolumeChartDTO;
import com.example.investment_api.search.detail.chart.controller.dto.TradingVolumeChartDTOs;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TradingVolumeChartParser {

    private final ObjectMapper objectMapper;

    public TradingVolumeChartParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public TradingVolumeChartDTOs parseChartData(String responseBody) throws IOException {
        return getDayChartDTO(responseBody);
    }

    private TradingVolumeChartDTOs getDayChartDTO(final String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        List<JsonNode> items = getJsonNodeElements(rootNode.path("output2"));
        return new TradingVolumeChartDTOs(extractChartData(items));
    }

    private List<JsonNode> getJsonNodeElements(final JsonNode itemsNode) {
        List<JsonNode> itemsList = new ArrayList<>();

        for (int i = 0; i < itemsNode.size(); i++) {
            itemsList.add(itemsNode.get(i));
        }
        return itemsList;
    }

    private List<TradingVolumeChartDTO> extractChartData(final List<JsonNode> items) {
        List<TradingVolumeChartDTO> chartDtos = new ArrayList<>();
        String previousAcmlVol = null;

        for (JsonNode item : items) {
            String cumulativeVolume = item.get("acml_vol").asText();
            String changeDirection = "increase";
            changeDirection = getChangeDirection(previousAcmlVol, cumulativeVolume, changeDirection);

            TradingVolumeChartDTO dto = new TradingVolumeChartDTO(
                    item.get("stck_bsop_date").asText(), // 주식 영업일자
                    cumulativeVolume,                      // 누적 거래량
                    changeDirection                      // 증가/감소 여부
            );
            chartDtos.add(dto);

            previousAcmlVol = cumulativeVolume;
        }

        return chartDtos;
    }

    private String getChangeDirection(String previousAcmlVol, String cumulativeVolume, String changeDirection) {
        if (previousAcmlVol != null) {
            long currentVol = Long.parseLong(cumulativeVolume);
            long previousVol = Long.parseLong(previousAcmlVol);

            if (currentVol > previousVol) {
                changeDirection = "increase";
            } else if (currentVol < previousVol) {
                changeDirection = "decrease";
            }
        }
        return changeDirection;
    }

}
