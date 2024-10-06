package com.example.investment_api.home.tradingVolume.config;

import com.example.investment_api.home.tradingVolume.controller.dto.TradingVolumeDTO;

import com.example.investment_api.home.tradingVolume.service.TradingVolumeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;

@Component
public class TradingVolumeWebSocketHandler extends TextWebSocketHandler {

    private final TradingVolumeService tradingVolumeService;

    private final ObjectMapper objectMapper;

    public TradingVolumeWebSocketHandler(final TradingVolumeService tradingVolumeService, ObjectMapper objectMapper) {
        this.tradingVolumeService = tradingVolumeService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketConnect(session);
    }

    private void webSocketConnect(final WebSocketSession session) throws IOException, InterruptedException {
        String approvalKey = getApprovalKeyFromSession(session);
        if (isValidApprovalKey(approvalKey)) {
            webSocketSession(session);
        } else {
            session.close();
        }
    }

    private String getApprovalKeyFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith("approval_key=")) {
                    return param.split("=")[1];
                }
            }
        }
        return null;
    }

    private boolean isValidApprovalKey(String approvalKey) {
        return "1de731cd-c41d-457f-bff8-1e5b0fa95327".equals(approvalKey);
    }

    private void webSocketSession(final WebSocketSession socketSource) throws IOException, InterruptedException {
        while (socketSource.isOpen()) {
            List<TradingVolumeDTO> tradingVolumeDTO = tradingVolumeService.getTradingVolume();
            String message = objectMapper.writeValueAsString(tradingVolumeDTO);
            if (socketSource.isOpen()) {
                socketSource.sendMessage(new TextMessage(message));
            }
            Thread.sleep(1000);
        }
    }
}
