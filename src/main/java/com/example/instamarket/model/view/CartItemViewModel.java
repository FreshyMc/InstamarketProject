package com.example.instamarket.model.view;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public class CartItemViewModel {
    private Long id;
    private String title;
    private BigDecimal price;
    private String sellerUsername;
    private String sellerProfilePicture;
    private Set<String> offerImages = new LinkedHashSet<>();
    private String optionKey;
    private String optionValue;
    private String offerCategory;
    private String shippingType;

    public CartItemViewModel() {
    }

    public Long getId() {
        return id;
    }

    public CartItemViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CartItemViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CartItemViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getOptionKey() {
        return optionKey;
    }

    public CartItemViewModel setOptionKey(String optionKey) {
        this.optionKey = optionKey;
        return this;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public CartItemViewModel setOptionValue(String optionValue) {
        this.optionValue = optionValue;
        return this;
    }

    public String getOfferCategory() {
        return offerCategory;
    }

    public CartItemViewModel setOfferCategory(String offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    public String getShippingType() {
        return shippingType;
    }

    public CartItemViewModel setShippingType(String shippingType) {
        this.shippingType = shippingType;
        return this;
    }

    public Set<String> getOfferImages() {
        return offerImages;
    }

    public CartItemViewModel setOfferImages(Set<String> offerImages) {
        this.offerImages = offerImages;
        return this;
    }

    public String getSellerProfilePicture() {
        return sellerProfilePicture;
    }

    public CartItemViewModel setSellerProfilePicture(String sellerProfilePicture) {
        this.sellerProfilePicture = sellerProfilePicture;
        return this;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public CartItemViewModel setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
        return this;
    }
}
