package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    private User sender;
    private User receiver;
    private String message;
    private LocalDateTime sentAt;

    public Message() {
    }

    @ManyToOne
    public User getSender() {
        return sender;
    }

    public Message setSender(User sender) {
        this.sender = sender;
        return this;
    }

    @ManyToOne
    public User getReceiver() {
        return receiver;
    }

    public Message setReceiver(User receiver) {
        this.receiver = receiver;
        return this;
    }

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    @Column(nullable = false)
    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Message setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
        return this;
    }
}
