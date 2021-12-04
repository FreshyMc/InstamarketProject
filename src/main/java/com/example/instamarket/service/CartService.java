package com.example.instamarket.service;

import com.example.instamarket.model.binding.AddToCartBindingModel;
import com.example.instamarket.model.dto.CartDTO;
import com.example.instamarket.model.service.CheckoutServiceModel;
import com.example.instamarket.model.view.CartItemViewModel;

import java.util.List;

public interface CartService {
    CartDTO addToCart(Long offerId, AddToCartBindingModel option, String username);

    CartDTO removeFromCart(Long cartItemId);

    List<CartItemViewModel> getAllItems(String username);

    void checkoutCart(CheckoutServiceModel model, String username);
}
