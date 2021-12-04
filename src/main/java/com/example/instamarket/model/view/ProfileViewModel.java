package com.example.instamarket.model.view;

public class ProfileViewModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;

    public ProfileViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public ProfileViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ProfileViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public ProfileViewModel setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ProfileViewModel setId(Long id) {
        this.id = id;
        return this;
    }
}
