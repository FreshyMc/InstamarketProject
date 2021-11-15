package com.example.instamarket.model.view;

public class ProfileNamesViewModel {
    private String firstName;
    private String lastName;

    public ProfileNamesViewModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public ProfileNamesViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileNamesViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
