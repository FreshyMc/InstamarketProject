package com.example.instamarket.model.view;

public class OfferImageViewModel {
    private Long id;
    private String imageUrl;

    public OfferImageViewModel() {
    }

    public Long getId() {
        return id;
    }

    public OfferImageViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferImageViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
