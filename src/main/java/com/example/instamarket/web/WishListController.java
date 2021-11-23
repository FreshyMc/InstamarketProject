package com.example.instamarket.web;

import com.example.instamarket.service.WishListService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist")
public class WishListController {
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping
    public String showWishList(Model model, @AuthenticationPrincipal InstamarketUser user){
        model.addAttribute("wishListItems", wishListService.getWishListItems(user.getUserIdentifier()));

        return "wishlist";
    }
}
