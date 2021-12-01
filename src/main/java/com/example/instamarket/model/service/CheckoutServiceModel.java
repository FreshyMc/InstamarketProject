package com.example.instamarket.model.service;

import java.util.LinkedList;
import java.util.List;

public class CheckoutServiceModel {
    private List<CheckoutItemServiceModel> cartItems = new LinkedList<>();
    private Long deliveryAddress;

    public CheckoutServiceModel() {
    }

    public List<CheckoutItemServiceModel> getCartItems() {
        return cartItems;
    }

    public CheckoutServiceModel setCartItems(List<CheckoutItemServiceModel> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public Long getDeliveryAddress() {
        return deliveryAddress;
    }

    public CheckoutServiceModel setDeliveryAddress(Long deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }
}
