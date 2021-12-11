package com.example.instamarket.schedules;

import com.example.instamarket.repository.CartRepository;
import com.example.instamarket.repository.WishListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CronDatabaseCleanup {
    private static final Logger LOGGER = LoggerFactory.getLogger(CronDatabaseCleanup.class);

    private final WishListRepository wishListRepository;
    private final CartRepository cartRepository;

    public CronDatabaseCleanup(WishListRepository wishListRepository, CartRepository cartRepository) {
        this.wishListRepository = wishListRepository;
        this.cartRepository = cartRepository;
    }

    @Scheduled(cron = "0 4 * * 7 *")
    public void clearItems(){
        wishListRepository.clearRemovedItems();
        cartRepository.clearRemovedItems();

        LOGGER.info("Database cleared successfully at {}", LocalDateTime.now());
    }
}
