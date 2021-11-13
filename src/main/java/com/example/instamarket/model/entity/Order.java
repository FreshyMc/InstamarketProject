package com.example.instamarket.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private User seller;
    private User buyer;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Address deliveryAddress;
    private LocalDateTime orderTime;
    private LocalDate deliveryDate;

    //TODO - Columns types also set nullables
    public Order() {
    }

    @ManyToOne
    public User getSeller() {
        return seller;
    }

    public Order setSeller(User seller) {
        this.seller = seller;
        return this;
    }

    @ManyToOne
    public User getBuyer() {
        return buyer;
    }

    public Order setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    @ManyToOne
    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public Order setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public Order setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Order setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }
}
