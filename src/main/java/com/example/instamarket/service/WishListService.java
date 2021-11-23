package com.example.instamarket.service;

import com.example.instamarket.model.view.WishListItemViewModel;

import java.util.List;
import java.util.Set;

public interface WishListService {
    List<WishListItemViewModel> getWishListItems(String username);
}
