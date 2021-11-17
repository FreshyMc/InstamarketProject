package com.example.instamarket.model.dto;

import com.example.instamarket.model.entity.OfferImage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OfferDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Set<String> images;
    private LocalDateTime createdAt;
    private String offerUrl;

    public OfferDTO() {
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public OfferDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public OfferDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<String> getImages() {
        return images;
    }

    public OfferDTO setImages(Set<String> images) {
        this.images = images;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OfferDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getOfferUrl() {
        return offerUrl;
    }

    public OfferDTO setOfferUrl(String offerUrl) {
        this.offerUrl = offerUrl;
        return this;
    }
}
