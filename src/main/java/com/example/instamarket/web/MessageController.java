package com.example.instamarket.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
    @GetMapping("/messaging")
    public String showMessagingPage(){
        return "message";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(){
        return null;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public String addUser(){
        return null;
    }
}
