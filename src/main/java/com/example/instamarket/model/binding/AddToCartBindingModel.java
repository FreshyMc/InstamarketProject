package com.example.instamarket.model.binding;

public class AddToCartBindingModel {
    private Integer offerOptionIndex;

    public AddToCartBindingModel() {
    }

    public Integer getOfferOptionIndex() {
        return offerOptionIndex;
    }

    public AddToCartBindingModel setOfferOptionIndex(Integer offerOptionIndex) {
        this.offerOptionIndex = offerOptionIndex;
        return this;
    }
}
