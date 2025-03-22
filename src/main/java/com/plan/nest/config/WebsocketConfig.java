package com.plan.nest.config;

import com.plan.nest.component.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebsocketConfig {

    @Bean
    public HandlerMapping webSocketMapping(ChatWebSocketHandler chatWebSocketHandler) {
        return new SimpleUrlHandlerMapping(Map.of("/ws/chat", chatWebSocketHandler), -1);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
