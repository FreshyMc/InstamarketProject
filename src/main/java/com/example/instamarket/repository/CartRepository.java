package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Cart;
import com.example.instamarket.model.entity.Offer;
import com.example.instamarket.model.entity.OfferOption;
import com.example.instamarket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByBuyerAndOfferAndOfferOption(User buyer, Offer offer, OfferOption offerOption);
}
