package com.example.instamarket.service;

import com.example.instamarket.model.dto.MessageDTO;

public interface MessageService {
    void saveMessage(MessageDTO model, String username);
}
