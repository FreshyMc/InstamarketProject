package com.example.instamarket.repository;

import com.example.instamarket.model.entity.OrderFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderFeedbackRepository extends JpaRepository<OrderFeedback, Long> {
    @Query("SELECT f FROM OrderFeedback f WHERE f.order.offer.id = :offerId ORDER BY f.id DESC")
    List<OrderFeedback> getOfferFeedback(@Param("offerId") Long offerId);
}
