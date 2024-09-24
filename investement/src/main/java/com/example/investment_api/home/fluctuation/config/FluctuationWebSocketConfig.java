package com.example.investment_api.home.fluctuation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class FluctuationWebSocketConfig implements WebSocketConfigurer {

    private final FluctuationWebSocketHandler fluctuationWebSocketHandler;

    public FluctuationWebSocketConfig(final FluctuationWebSocketHandler fluctuationWebSocketHandler) {
        this.fluctuationWebSocketHandler = fluctuationWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(fluctuationWebSocketHandler,"/ws/fluctuation").setAllowedOrigins("*");
    }
}
