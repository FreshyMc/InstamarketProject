package com.example.instamarket.model.entity;

import com.example.instamarket.model.enums.OrderStatusEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "order_status")
public class OrderStatus extends BaseEntity{
    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum name;

    public OrderStatus() {
    }

    public OrderStatusEnum getName() {
        return name;
    }

    public OrderStatus setName(OrderStatusEnum name) {
        this.name = name;
        return this;
    }
}
