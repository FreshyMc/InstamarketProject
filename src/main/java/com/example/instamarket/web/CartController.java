package com.example.instamarket.web;

import com.example.instamarket.service.impl.InstamarketUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    @GetMapping
    public String showCart(Model model, @AuthenticationPrincipal InstamarketUser user){
        return "cart";
    }
}
