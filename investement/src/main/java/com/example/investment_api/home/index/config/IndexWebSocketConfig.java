package com.example.investment_api.home.index.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class IndexWebSocketConfig implements WebSocketConfigurer {

    private final IndexWebSocketHandler indexWebSocketHandler;

    public IndexWebSocketConfig(final IndexWebSocketHandler indexWebSocketHandler) {
        this.indexWebSocketHandler = indexWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(indexWebSocketHandler, "/wss/index");
    }
}
