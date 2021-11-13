package com.example.instamarket.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class OptionBindingModel {
    private MultipartFile image;
    private String value;

    public OptionBindingModel() {
    }

    public MultipartFile getImage() {
        return image;
    }

    public OptionBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public String getValue() {
        return value;
    }

    public OptionBindingModel setValue(String value) {
        this.value = value;
        return this;
    }
}
