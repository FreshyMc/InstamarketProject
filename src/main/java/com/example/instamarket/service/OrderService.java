package com.example.instamarket.service;

import com.example.instamarket.model.view.OrderViewModel;

import java.util.List;

public interface OrderService {

    void initializeOfferStatus();

    List<OrderViewModel> showRecentOrders(String username);

    List<OrderViewModel> getSellerRecentOrders(String username);

    void acceptOrder(Long orderId, String username);

    List<OrderViewModel> getSellerAcceptedOrders(String username);

    void cancelOrder(Long orderId, String username);

    void shipOrder(Long orderId, String username);

    void completeOrder(Long orderId, String username);
}
