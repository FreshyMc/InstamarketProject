package com.example.instamarket.websocket;

import com.example.instamarket.model.dto.MessageDTO;
import com.example.instamarket.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebsocketHandler extends TextWebSocketHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(WebsocketHandler.class);

    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public WebsocketHandler(MessageService messageService, ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String senderUsername = session.getPrincipal().getName();

        if(senderUsername != null){
            String rawPayload = message.getPayload();

            MessageDTO model = objectMapper.readValue(rawPayload, MessageDTO.class);

            try{
                messageService.saveMessage(model, senderUsername);
            }catch (Exception ex){
                LOGGER.error(ex.getMessage());
            }
        }
    }
}
