package com.example.investment_api.home.index.infrastructure;

import com.example.investment_api.home.index.controller.dto.KOSDAQResponse;
import com.example.investment_api.home.index.controller.dto.KOSPIResponse;

import com.example.investment_api.home.index.service.IndexService;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class IndexWebSocketHandler extends TextWebSocketHandler {

    private final IndexService indexService;

    public IndexWebSocketHandler(IndexService indexService) {
        this.indexService = indexService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        while (session.isOpen()) {
            KOSPIResponse kospi = indexService.getKOSPIIndex();
            KOSDAQResponse kosdaq = indexService.getKOSDAQIndex();
            String message = String.format("{\"kospi\": %s, \"kosdaq\": %s}",
                    kospi.toString(),
                    kosdaq.toString());
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
            Thread.sleep(1000);
        }
    }

}
