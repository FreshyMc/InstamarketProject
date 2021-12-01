package com.example.instamarket.model.binding;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

public class CheckoutBindingModel {
    private List<CheckoutItemBindingModel> cartItems = new LinkedList<>();
    @NotNull
    private Long deliveryAddress;

    public CheckoutBindingModel() {
    }

    public List<CheckoutItemBindingModel> getCartItems() {
        return cartItems;
    }

    public CheckoutBindingModel setCartItems(List<CheckoutItemBindingModel> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public Long getDeliveryAddress() {
        return deliveryAddress;
    }

    public CheckoutBindingModel setDeliveryAddress(Long deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }
}
