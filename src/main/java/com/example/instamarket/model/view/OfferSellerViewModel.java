package com.example.instamarket.model.view;

public class OfferSellerViewModel {
    private Long id;
    private String username;
    private String profilePictureUrl;
    private Integer subscriberCount;
    private Integer offerCount;

    public OfferSellerViewModel() {
    }

    public Long getId() {
        return id;
    }

    public OfferSellerViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public OfferSellerViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public OfferSellerViewModel setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
        return this;
    }

    public Integer getSubscriberCount() {
        return subscriberCount;
    }

    public OfferSellerViewModel setSubscriberCount(Integer subscriberCount) {
        this.subscriberCount = subscriberCount;
        return this;
    }

    public Integer getOfferCount() {
        return offerCount;
    }

    public OfferSellerViewModel setOfferCount(Integer offerCount) {
        this.offerCount = offerCount;
        return this;
    }
}
