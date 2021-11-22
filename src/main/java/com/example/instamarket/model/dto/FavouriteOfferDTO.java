package com.example.instamarket.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class FavouriteOfferDTO{
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Set<String> images;
    private LocalDateTime createdAt;
    private String offerUrl;
    private Boolean isFavourite;

    public FavouriteOfferDTO() {
    }

    public Long getId() {
        return id;
    }

    public FavouriteOfferDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FavouriteOfferDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public FavouriteOfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FavouriteOfferDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<String> getImages() {
        return images;
    }

    public FavouriteOfferDTO setImages(Set<String> images) {
        this.images = images;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public FavouriteOfferDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getOfferUrl() {
        return offerUrl;
    }

    public FavouriteOfferDTO setOfferUrl(String offerUrl) {
        this.offerUrl = offerUrl;
        return this;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public FavouriteOfferDTO setFavourite(Boolean favourite) {
        isFavourite = favourite;
        return this;
    }
}
