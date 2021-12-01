package com.example.instamarket.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CheckoutItemBindingModel {
    @NotNull
    private Long offerId;
    @Positive
    private Integer quantity;
    private Integer offerOptionIndex;

    public CheckoutItemBindingModel() {
    }

    public Long getOfferId() {
        return offerId;
    }

    public CheckoutItemBindingModel setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CheckoutItemBindingModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getOfferOptionIndex() {
        return offerOptionIndex;
    }

    public CheckoutItemBindingModel setOfferOptionIndex(Integer offerOptionIndex) {
        this.offerOptionIndex = offerOptionIndex;
        return this;
    }
}
