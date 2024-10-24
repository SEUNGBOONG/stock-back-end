package com.example.investment_api.home.marketCapitalization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class MarketCapitalizationWebSocketConfig implements WebSocketConfigurer {

    private final MarketCapitalizationWebSocketHandler marketCapitalizationWebSocketHandler;

    public MarketCapitalizationWebSocketConfig(final MarketCapitalizationWebSocketHandler marketCapitalizationWebSocketHandler) {
        this.marketCapitalizationWebSocketHandler = marketCapitalizationWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(marketCapitalizationWebSocketHandler, "/ws/marketCapitalization")
                .setAllowedOrigins("http://13.209.73.178:80")
                .setAllowedOrigins("http://localhost:3000");
    }
}
