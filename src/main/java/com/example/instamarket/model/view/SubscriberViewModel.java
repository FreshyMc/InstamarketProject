package com.example.instamarket.model.view;

public class SubscriberViewModel {
    private Long subscriberId;
    private String username;
    private String profilePictureUrl;

    public SubscriberViewModel() {
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public SubscriberViewModel setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SubscriberViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public SubscriberViewModel setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
        return this;
    }
}
