package com.example.investment_api.home.fluctuation.config;

import com.example.investment_api.home.fluctuation.controller.dto.response.FluctuationDTO;
import com.example.investment_api.home.fluctuation.service.FluctuationService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;

@Component
public class FluctuationWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final FluctuationService fluctuationService;

    public FluctuationWebSocketHandler(final ObjectMapper objectMapper, final FluctuationService fluctuationService) {
        this.objectMapper = objectMapper;
        this.fluctuationService = fluctuationService;
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
        return "85337d24-1d88-4793-acbd-3202ae289251".equals(approvalKey);
    }

    private void webSocketSession(final WebSocketSession socketSource) throws IOException, InterruptedException {
        while (socketSource.isOpen()) {
            List<FluctuationDTO> fluctuationDTO = fluctuationService.getFluctuation();
            String message = objectMapper.writeValueAsString(fluctuationDTO);
            if (socketSource.isOpen()) {
                socketSource.sendMessage(new TextMessage(message));
            }
            Thread.sleep(1000);
        }
    }

}
