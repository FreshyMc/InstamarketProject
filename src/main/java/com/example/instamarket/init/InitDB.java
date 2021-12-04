package com.example.instamarket.init;

import com.example.instamarket.service.OfferService;
import com.example.instamarket.service.OrderService;
import com.example.instamarket.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner {
    private final UserService userService;
    private final OfferService offerService;
    private final OrderService orderService;

    public InitDB(UserService userService, OfferService offerService, OrderService orderService) {
        this.userService = userService;
        this.offerService = offerService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        //Initialization of user roles
        userService.initializeRoles();
        //Initialization of offer categories
        offerService.initializeOfferCategories();
        //Initialization of order status
        orderService.initializeOfferStatus();
    }
}
