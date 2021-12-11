package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Cart;
import com.example.instamarket.model.entity.Offer;
import com.example.instamarket.model.entity.OfferOption;
import com.example.instamarket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByBuyerAndOfferAndOfferOption(User buyer, Offer offer, OfferOption offerOption);

    List<Cart> findAllByBuyerAndRemovedOrderByAddedAtDesc(User buyer, boolean removed);

    @Query("SELECT c FROM Cart c WHERE c.buyer = :buyer AND c.removed = false AND c.offer.deleted = false ORDER BY c.addedAt DESC")
    List<Cart> findAllCartItems(@Param("buyer") User buyer);

    @Query(value = "DELETE FROM Cart c WHERE c.removed = true")
    void clearRemovedItems();
}
