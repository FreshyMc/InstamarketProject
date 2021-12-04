package com.example.instamarket.service;

import com.example.instamarket.model.view.OrderViewModel;

import java.util.List;

public interface OrderService {

    void initializeOfferStatus();

    List<OrderViewModel> showRecentOrders(String username);
}
