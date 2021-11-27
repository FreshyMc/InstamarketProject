package com.example.instamarket.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePictureBindingModel {
    private MultipartFile profilePicture;

    public ProfilePictureBindingModel() {
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public ProfilePictureBindingModel setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
}
