package com.example.investment_api.common.config;

import com.example.investment_api.home.index.infrastructure.IndexWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final IndexWebSocketHandler indexWebSocketHandler;

    public WebSocketConfig(final IndexWebSocketHandler indexWebSocketHandler) {
        this.indexWebSocketHandler = indexWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(indexWebSocketHandler, "/ws/index").setAllowedOrigins("*");
    }
}
