package com.example.instamarket.model.dto;

public class MessageDTO {
    private Long id;
    private String receiver;
    private String message;

    public MessageDTO() {
    }

    public Long getId() {
        return id;
    }

    public MessageDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getReceiver() {
        return receiver;
    }

    public MessageDTO setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageDTO setMessage(String message) {
        this.message = message;
        return this;
    }
}
