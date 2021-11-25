package com.example.instamarket.service;

import com.example.instamarket.model.binding.AddToCartBindingModel;
import com.example.instamarket.model.dto.CartDTO;
import com.example.instamarket.model.view.CartItemViewModel;

import java.util.List;

public interface CartService {
    CartDTO addToCart(Long offerId, AddToCartBindingModel option, String username);

    CartDTO removeFromCart(Long offerId, AddToCartBindingModel option, String username);

    List<CartItemViewModel> getAllItems(String username);
}
