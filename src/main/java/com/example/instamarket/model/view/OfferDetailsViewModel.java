package com.example.instamarket.model.view;

import java.math.BigDecimal;
import java.util.*;

public class OfferDetailsViewModel {
    private Long id;
    private String title;
    private String price;
    private String description;
    private Set<String> offerImages = new LinkedHashSet<>();
    private Map<String, String> options = new LinkedHashMap<>();
    private Map<String, String> properties = new LinkedHashMap<>();
    private String offerCategory;
    private String shippingType;
    private boolean isOwner;

    public OfferDetailsViewModel() {
    }

    public Long getId() {
        return id;
    }

    public OfferDetailsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public OfferDetailsViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public OfferDetailsViewModel setPrice(String price) {
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
