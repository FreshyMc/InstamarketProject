package com.example.instamarket.model.service;

public class UserRegisterServiceModel {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String country;
    private String postalCode;
    private String city;
    private String street;

    public UserRegisterServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserRegisterServiceModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public UserRegisterServiceModel setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserRegisterServiceModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public UserRegisterServiceModel setStreet(String street) {
        this.street = street;
        return this;
    }
}
