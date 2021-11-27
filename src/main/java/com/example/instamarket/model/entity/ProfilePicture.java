package com.example.instamarket.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicture extends BaseEntity{
    private String url;
    private String publicId;
    private boolean isDefault = true;

    public ProfilePicture() {
    }

    public String getUrl() {
        return url;
    }

    public ProfilePicture setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public ProfilePicture setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public ProfilePicture setDefault(boolean aDefault) {
        isDefault = aDefault;
        return this;
    }
}
