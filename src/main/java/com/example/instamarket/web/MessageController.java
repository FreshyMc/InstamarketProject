package com.example.instamarket.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
    @GetMapping("/messaging")
    public String showMessagingPage(){
        return "message";
    }
}
