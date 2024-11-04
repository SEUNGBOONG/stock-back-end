package com.example.investment_api.search.detail.enterpriseInfo.infrastructure;

import com.example.investment_api.search.detail.enterpriseInfo.controller.dto.EnterpriseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class EnterpriseParser {

    private final ObjectMapper objectMapper;

    public EnterpriseParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<EnterpriseDTO> parseEnterpriseInfo(String responseBody) throws IOException {
        return setJsonNode(responseBody);
    }

    private List<EnterpriseDTO> setJsonNode(final String responseBody) throws JsonProcessingException {
        JsonNode items = objectMapper.readTree(responseBody).path("items");

        List<EnterpriseDTO> enterpriseList = new ArrayList<>();
        Iterator<JsonNode> elements = items.elements();

        extractEnterprise(elements, enterpriseList);
        return enterpriseList;
    }

    private void extractEnterprise(final Iterator<JsonNode> elements, final List<EnterpriseDTO> enterpriseDTOS) {
        while (elements.hasNext()) {
            JsonNode newsItem = elements.next();

            String industry = newsItem.path("std_idst_clsf_cd_name").asText().replaceAll("<.*?>", "");
            String marketCapitalization = newsItem.path("idx_bztp_lcls_cd_name").asText();
            String major = newsItem.path("prdt_name").asText();

            enterpriseDTOS.add(new EnterpriseDTO(industry, marketCapitalization, major));
        }
    }
}
