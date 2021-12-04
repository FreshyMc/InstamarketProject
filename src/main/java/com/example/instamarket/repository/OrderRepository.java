package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Order;
import com.example.instamarket.model.entity.OrderStatus;
import com.example.instamarket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByBuyerAndStatusNot(User buyer, OrderStatus status);
}
