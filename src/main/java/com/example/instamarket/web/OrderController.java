package com.example.instamarket.web;

import com.example.instamarket.service.OrderService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String showOrdersPage(Model model, @AuthenticationPrincipal InstamarketUser user){
        model.addAttribute("recentOrders", orderService.showRecentOrders(user.getUserIdentifier()));
        model.addAttribute("completedOrders", orderService.showCompletedOrders(user.getUserIdentifier()));

        return "orders";
    }

    @GetMapping("/orders/{orderId}/received")
    public String completeOrder(@PathVariable Long orderId, @AuthenticationPrincipal InstamarketUser user){
        orderService.completeOrder(orderId, user.getUserIdentifier());

        return "redirect:/orders";
    }

    @GetMapping("/seller/orders")
    public String showSellerOrders(Model model, @AuthenticationPrincipal InstamarketUser user){
        model.addAttribute("recentOrders", orderService.getSellerRecentOrders(user.getUserIdentifier()));
        model.addAttribute("acceptedOrders", orderService.getSellerAcceptedOrders(user.getUserIdentifier()));
        model.addAttribute("completedOrders", orderService.getSellerCompletedOrders(user.getUserIdentifier()));

        return "seller/orders";
    }

    @GetMapping("/seller/orders/{orderId}/accept")
    public String acceptOrder(@PathVariable Long orderId, @AuthenticationPrincipal InstamarketUser user){
        orderService.acceptOrder(orderId, user.getUserIdentifier());

        return "redirect:/seller/orders";
    }

    @GetMapping("/seller/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId, @AuthenticationPrincipal InstamarketUser user){
        orderService.cancelOrder(orderId, user.getUserIdentifier());

        return "redirect:/seller/orders";
    }

    @GetMapping("/seller/orders/{orderId}/ship")
    public String shipOrder(@PathVariable Long orderId, @AuthenticationPrincipal InstamarketUser user){
        orderService.shipOrder(orderId, user.getUserIdentifier());

        return "redirect:/seller/orders";
    }
}
