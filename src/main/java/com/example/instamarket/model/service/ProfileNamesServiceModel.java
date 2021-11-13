package com.example.instamarket.model.service;

public class ProfileNamesServiceModel {
    private String firstName;
    private String lastName;

    public ProfileNamesServiceModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public ProfileNamesServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileNamesServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
