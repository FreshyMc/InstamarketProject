package com.example.instamarket.config;

import com.example.instamarket.websocket.WebsocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration
public class WebsocketConfig implements WebSocketConfigurer {
    private final WebsocketHandler websocketHandler;

    public WebsocketConfig(WebsocketHandler websocketHandler) {
        this.websocketHandler = websocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //TODO
        registry.addHandler(websocketHandler, "/socket/demo");
    }
}
