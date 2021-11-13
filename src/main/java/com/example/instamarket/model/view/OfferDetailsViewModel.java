package com.example.instamarket.model.view;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OfferDetailsViewModel {
    private String title;
    private BigDecimal price;
    private String description;
    private Set<String> offerImages = new HashSet<>();
    private Map<String, String> options = new HashMap<>();
    private Map<String, String> properties = new HashMap<>();
    private String offerCategory;
    private String shippingType;
    private boolean isOwner;

    public OfferDetailsViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public OfferDetailsViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferDetailsViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<String> getOfferImages() {
        return offerImages;
    }

    public OfferDetailsViewModel setOfferImages(Set<String> offerImages) {
        this.offerImages = offerImages;
        return this;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public OfferDetailsViewModel setOptions(Map<String, String> options) {
        this.options = options;
        return this;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public OfferDetailsViewModel setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public String getOfferCategory() {
        return offerCategory;
    }

    public OfferDetailsViewModel setOfferCategory(String offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    public String getShippingType() {
        return shippingType;
    }

    public OfferDetailsViewModel setShippingType(String shippingType) {
        this.shippingType = shippingType;
        return this;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public OfferDetailsViewModel setOwner(boolean owner) {
        isOwner = owner;
        return this;
    }
}
