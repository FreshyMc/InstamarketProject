package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String message;
    @Column(nullable = false)
    private LocalDateTime sentAt;

    public Message() {
    }

    public User getSender() {
        return sender;
    }

    public Message setSender(User sender) {
        this.sender = sender;
        return this;
    }

    public User getReceiver() {
        return receiver;
    }

    public Message setReceiver(User receiver) {
        this.receiver = receiver;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Message setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
        return this;
    }
}
