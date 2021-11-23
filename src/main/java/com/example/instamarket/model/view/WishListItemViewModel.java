package com.example.instamarket.model.view;

import com.example.instamarket.model.entity.OfferImage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class WishListItemViewModel {
    private Long id;
    private String title;
    private String price;
    private String description;
    private Set<String> images;
    private LocalDateTime addedAt;

    public WishListItemViewModel() {
    }

    public Long getId() {
        return id;
    }

    public WishListItemViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public WishListItemViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public WishListItemViewModel setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WishListItemViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<String> getImages() {
        return images;
    }

    public WishListItemViewModel setImages(Set<String> images) {
        this.images = images;
        return this;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public WishListItemViewModel setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
        return this;
    }
}
