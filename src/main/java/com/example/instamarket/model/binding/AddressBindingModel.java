package com.example.instamarket.model.binding;

import javax.validation.constraints.NotBlank;

public class AddressBindingModel {
    @NotBlank
    private String country;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    private String details;

    public AddressBindingModel() {
    }

    public String getCountry() {
        return country;
    }

    public AddressBindingModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public AddressBindingModel setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressBindingModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressBindingModel setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public AddressBindingModel setDetails(String details) {
        this.details = details;
        return this;
    }
}
