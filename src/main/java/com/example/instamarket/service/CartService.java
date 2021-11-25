package com.example.instamarket.service;

import com.example.instamarket.model.binding.AddToCartBindingModel;
import com.example.instamarket.model.dto.CartDTO;

public interface CartService {
    CartDTO addToCart(Long offerId, AddToCartBindingModel option, String username);

    CartDTO removeFromCart(Long offerId, AddToCartBindingModel option, String username);
}
