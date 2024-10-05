package com.example.investment_api.home.tradingVolume.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;

import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class TradingVolumeWebSocketConfig implements WebSocketConfigurer {

    private final TradingVolumeWebSocketHandler tradingVolumeWebSocketHandler;

    public TradingVolumeWebSocketConfig(final TradingVolumeWebSocketHandler tradingVolumeWebSocketHandler) {
        this.tradingVolumeWebSocketHandler = tradingVolumeWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(tradingVolumeWebSocketHandler, "/ws/tradingVolume").setAllowedOrigins("*");
    }
}
