package com.example.instamarket.service;

public interface SubscriberService {
    void subscribe(Long sellerId, String username);

    boolean isSubscribed(Long sellerId, String username);

    void unsubscribe(Long sellerId, String username);
}
