package com.example.instamarket.repository;

import com.example.instamarket.model.entity.OrderFeedback;
import com.example.instamarket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderFeedbackRepository extends JpaRepository<OrderFeedback, Long> {
    @Query("SELECT f FROM OrderFeedback f WHERE f.order.offer.id = :offerId ORDER BY f.id DESC")
    List<OrderFeedback> getOfferFeedback(@Param("offerId") Long offerId);

    @Query("SELECT AVG(f.rating) FROM OrderFeedback f WHERE f.order.offer.seller = :seller")
    Optional<Double> getSellerPositiveFeedback(@Param("seller") User seller);
}
