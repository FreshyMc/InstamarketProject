package com.example.instamarket.web;

import com.example.instamarket.service.OrderService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String showOrdersPage(Model model, @AuthenticationPrincipal InstamarketUser user){
        model.addAttribute("recentOrders", orderService.showRecentOrders(user.getUserIdentifier()));

        return "orders";
    }
}
