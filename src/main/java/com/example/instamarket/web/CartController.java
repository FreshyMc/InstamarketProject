package com.example.instamarket.web;

import com.example.instamarket.service.AddressService;
import com.example.instamarket.service.CartService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final AddressService addressService;

    public CartController(CartService cartService, AddressService addressService) {
        this.cartService = cartService;
        this.addressService = addressService;
    }

    @GetMapping
    public String showCart(Model model, @AuthenticationPrincipal InstamarketUser user){
        model.addAttribute("cartItems", cartService.getAllItems(user.getUserIdentifier()));
        model.addAttribute("deliveryAddresses", addressService.findAllUserAddresses(user.getUserIdentifier()));

        return "cart";
    }
}
