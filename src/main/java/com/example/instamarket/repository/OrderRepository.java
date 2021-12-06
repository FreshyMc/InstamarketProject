package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Order;
import com.example.instamarket.model.entity.OrderStatus;
import com.example.instamarket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByBuyerAndStatusNot(User buyer, OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.offer.seller = :seller AND o.status = :status ORDER BY o.orderTime DESC")
    List<Order> findAllSellerOrdersByStatus(@Param("seller") User seller, @Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.offer.seller = :seller AND o.id = :orderId")
    Optional<Order> findSellerOrder(@Param("seller") User seller, @Param("orderId") Long orderId);

    @Query("SELECT o FROM Order o WHERE o.buyer = :buyer AND o.id = :orderId")
    Optional<Order> findBuyerOrder(@Param("buyer") User buyer, @Param("orderId") Long orderId);
}
