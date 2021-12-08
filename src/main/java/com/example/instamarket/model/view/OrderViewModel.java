package com.example.instamarket.model.view;

import com.example.instamarket.model.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class OrderViewModel {
    private Long id;
    private Long offerId;
    private String offerTitle;
    private Set<String> offerImages = new LinkedHashSet<>();
    private String optionKey;
    private String optionValue;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String orderStatus;
    private String deliveryAddress;
    private LocalDateTime orderTime;
    private LocalDate deliveryDate;
    private boolean isShipped = false;
    private boolean addedFeedback;

    public OrderViewModel() {
    }

    public Set<String> getOfferImages() {
        return offerImages;
    }

    public OrderViewModel setOfferImages(Set<String> offerImages) {
        this.offerImages = offerImages;
        return this;
    }

    public String getOptionKey() {
        return optionKey;
    }

    public OrderViewModel setOptionKey(String optionKey) {
        this.optionKey = optionKey;
        return this;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public OrderViewModel setOptionValue(String optionValue) {
        this.optionValue = optionValue;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderViewModel setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public OrderViewModel setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public OrderViewModel setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public OrderViewModel setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public OrderViewModel setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
        return this;
    }

    public Long getOfferId() {
        return offerId;
    }

    public OrderViewModel setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public OrderViewModel setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public Long getId() {
        return id;
    }

    public OrderViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public OrderViewModel setShipped(boolean shipped) {
        isShipped = shipped;
        return this;
    }

    public boolean isAddedFeedback() {
        return addedFeedback;
    }

    public OrderViewModel setAddedFeedback(boolean addedFeedback) {
        this.addedFeedback = addedFeedback;
        return this;
    }
}
