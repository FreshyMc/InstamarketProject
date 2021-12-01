package com.example.instamarket.model.service;

public class CheckoutItemServiceModel {
    private Long offerId;
    private Integer quantity;
    private Integer offerOptionIndex;

    public CheckoutItemServiceModel() {
    }

    public Long getOfferId() {
        return offerId;
    }

    public CheckoutItemServiceModel setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CheckoutItemServiceModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getOfferOptionIndex() {
        return offerOptionIndex;
    }

    public CheckoutItemServiceModel setOfferOptionIndex(Integer offerOptionIndex) {
        this.offerOptionIndex = offerOptionIndex;
        return this;
    }
}
