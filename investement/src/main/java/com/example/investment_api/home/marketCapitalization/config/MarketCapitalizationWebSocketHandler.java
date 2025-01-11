package com.example.investment_api.home.marketCapitalization.config;

import com.example.investment_api.home.marketCapitalization.controller.dto.MarketCapitalizationDTO;

import com.example.investment_api.home.marketCapitalization.service.MarketCapitalizationService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;

@Component
public class MarketCapitalizationWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final MarketCapitalizationService marketCapitalizationService;

    @Value("${WEBSOCKET_KEY}")
    private String socketKey;

    @Autowired
    public MarketCapitalizationWebSocketHandler(final ObjectMapper objectMapper, final MarketCapitalizationService marketCapitalizationService) {
        this.objectMapper = objectMapper;
        this.marketCapitalizationService = marketCapitalizationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketConnect(session);
    }

    private void webSocketConnect(final WebSocketSession session) throws IOException, InterruptedException {
        String[] approvalKeyFromSession = getApprovalKeyFromSession(session);
        String approvalKey = getParamsKey(approvalKeyFromSession);
        if (isValidApprovalKey(approvalKey)) {
            webSocketSession(session);
        } else {
            session.close();
        }
    }

    private String[] getApprovalKeyFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        String[] params = new String[0];
        if (query != null) {
            params = query.split("&");
        }
        return params;
    }

    private String getParamsKey(String[] params) {
        for (String param : params) {
            if (param.startsWith("approval_key=")) {
                return param.split("=")[1];
            }
        }
        return null;
    }

    private boolean isValidApprovalKey(String approvalKey) {
        return socketKey.equals(approvalKey);
    }

    private void webSocketSession(final WebSocketSession socketSource) throws IOException, InterruptedException {
        while (socketSource.isOpen()) {
            List<MarketCapitalizationDTO> marketCapitalizationDTOList = marketCapitalizationService.getMarketCapitalization();
            String message = objectMapper.writeValueAsString(marketCapitalizationDTOList);
            if (socketSource.isOpen()) {
                socketSource.sendMessage(new TextMessage(message));
            }
            Thread.sleep(1000);
        }
    }
}
