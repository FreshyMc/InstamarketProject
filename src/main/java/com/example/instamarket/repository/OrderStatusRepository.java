package com.example.instamarket.repository;

import com.example.instamarket.model.entity.OrderStatus;
import com.example.instamarket.model.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findOrderStatusByName(OrderStatusEnum name);
}
