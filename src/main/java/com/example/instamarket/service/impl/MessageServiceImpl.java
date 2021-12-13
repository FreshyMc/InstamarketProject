package com.example.instamarket.service.impl;

import com.example.instamarket.exception.UserNotFoundException;
import com.example.instamarket.model.dto.MessageDTO;
import com.example.instamarket.model.entity.Message;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.repository.MessageRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(UserRepository userRepository, MessageRepository messageRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveMessage(MessageDTO model, String username) {
        User sender = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());

        User receiver = userRepository.findByUsername(model.getReceiver()).orElseThrow(() -> new UserNotFoundException());

        Message message = new Message();

        message.
                setMessage(model.getMessage()).
                setSender(sender).
                setReceiver(receiver);

        messageRepository.save(message);
    }
}
