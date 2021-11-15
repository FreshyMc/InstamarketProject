package com.example.instamarket.model.view;

public class ProfileAddressesViewModel {
    private Long id;
    private String country;
    private String postalCode;
    private String city;
    private String street;
    private String details;

    public ProfileAddressesViewModel() {
    }

    public String getCountry() {
        return country;
    }

    public ProfileAddressesViewModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public ProfileAddressesViewModel setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ProfileAddressesViewModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public ProfileAddressesViewModel setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ProfileAddressesViewModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ProfileAddressesViewModel setId(Long id) {
        this.id = id;
        return this;
    }
}
