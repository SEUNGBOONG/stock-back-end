package com.example.investment.home.fluctuation.infrastructor;

import com.example.investment.home.fluctuation.controller.dto.response.FluctuationDTO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FluctuationParserTest {

    @InjectMocks
    private FluctuationParser fluctuationParser;

    @Mock
    private ObjectMapper objectMapper;

    public FluctuationParserTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFluctuation() throws IOException {
        //given
        String jsonResponse = "{ \"output\": [ { \"hts_kor_isnm\": \"StockA\", \"data_rank\": \"1\", \"stck_prpr\": \"100\", \"prdy_vrss\": \"5\", \"prdy_vrss_sign\": \"+\", \"prdy_ctrt\": \"0.05\" }, { \"hts_kor_isnm\": \"StockB\", \"data_rank\": \"2\", \"stck_prpr\": \"200\", \"prdy_vrss\": \"-3\", \"prdy_vrss_sign\": \"-\", \"prdy_ctrt\": \"-0.02\" } ] }";
        JsonNode mockNode = mock(JsonNode.class);
        JsonNode mockItemsNode = mock(JsonNode.class);

        //when
        when(objectMapper.readTree(jsonResponse)).thenReturn(mockNode);
        when(mockNode.path("output")).thenReturn(mockItemsNode);
        when(mockItemsNode.elements()).thenReturn(mockJsonNodes());

        List<FluctuationDTO> result = fluctuationParser.getFluctuation(jsonResponse);


        //then
        assertEquals(2, result.size());
        assertEquals(new FluctuationDTO("StockA", 1, 100, 5, "+", 0.05), result.get(0));
        assertEquals(new FluctuationDTO("StockB", 2, 200, -3, "-", -0.02), result.get(1));
    }

    private Iterator<JsonNode> mockJsonNodes() {

        //given
        JsonNode item1 = mock(JsonNode.class);
        JsonNode item2 = mock(JsonNode.class);

        //when
        when(item1.path("hts_kor_isnm")).thenReturn(mockTextNode("StockA"));
        when(item1.path("data_rank")).thenReturn(mockTextNode("1"));
        when(item1.path("stck_prpr")).thenReturn(mockTextNode("100"));
        when(item1.path("prdy_vrss")).thenReturn(mockTextNode("5"));
        when(item1.path("prdy_vrss_sign")).thenReturn(mockTextNode("+"));
        when(item1.path("prdy_ctrt")).thenReturn(mockTextNode("0.05"));

        when(item2.path("hts_kor_isnm")).thenReturn(mockTextNode("StockB"));
        when(item2.path("data_rank")).thenReturn(mockTextNode("2"));
        when(item2.path("stck_prpr")).thenReturn(mockTextNode("200"));
        when(item2.path("prdy_vrss")).thenReturn(mockTextNode("-3"));
        when(item2.path("prdy_vrss_sign")).thenReturn(mockTextNode("-"));
        when(item2.path("prdy_ctrt")).thenReturn(mockTextNode("-0.02"));

        //then
        return List.of(item1, item2).iterator();
    }

    private JsonNode mockTextNode(String text) {
        JsonNode textNode = mock(JsonNode.class);
        when(textNode.asText()).thenReturn(text);
        return textNode;
    }
}
