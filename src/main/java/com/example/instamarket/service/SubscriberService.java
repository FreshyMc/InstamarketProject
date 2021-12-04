package com.example.instamarket.service;

import com.example.instamarket.model.view.SubscriberViewModel;

import java.util.List;

public interface SubscriberService {
    void subscribe(Long sellerId, String username);

    boolean isSubscribed(Long sellerId, String username);

    void unsubscribe(Long sellerId, String username);

    List<SubscriberViewModel> listSubscribers(String username);
}
