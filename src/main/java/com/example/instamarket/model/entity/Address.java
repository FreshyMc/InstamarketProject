package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {
    private User user;
    private String country;
    private String postalCode;
    private String city;
    private String street;
    private String details;

    public Address() {
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public Address setUser(User user) {
        this.user = user;
        return this;
    }

    @Column(nullable = false, length = 100)
    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    @Column(nullable = false)
    public String getPostalCode() {
        return postalCode;
    }

    public Address setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    @Column(nullable = false)
    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    @Column(columnDefinition = "TEXT")
    public String getDetails() {
        return details;
    }

    public Address setDetails(String details) {
        this.details = details;
        return this;
    }

    @Column(nullable = false)
    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }
}
