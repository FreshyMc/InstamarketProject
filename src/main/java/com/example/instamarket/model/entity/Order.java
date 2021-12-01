package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @ManyToOne
    private User buyer;
    @Column(nullable = false)
    private Integer quantity;
    @ManyToOne
    private Offer offer;
    @ManyToOne
    private OfferOption offerOption;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @ManyToOne
    private Address deliveryAddress;
    private LocalDateTime orderTime;
    private LocalDate deliveryDate;
    @Column
    private boolean delivered = false;

    public Order() {
    }

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

    public boolean isDelivered() {
        return delivered;
    }

    public Order setDelivered(boolean delivered) {
        this.delivered = delivered;
        return this;
    }

    public Offer getOffer() {
        return offer;
    }

    public Order setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @PrePersist
    public void populateOrderTime(){
        this.setOrderTime(LocalDateTime.now());
    }

    public OfferOption getOfferOption() {
        return offerOption;
    }

    public Order setOfferOption(OfferOption offerOption) {
        this.offerOption = offerOption;
        return this;
    }
}
