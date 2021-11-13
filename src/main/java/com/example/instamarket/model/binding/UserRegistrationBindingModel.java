package com.example.instamarket.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationBindingModel {
    @NotNull
    @Size(min = 2, max = 30)
    private String username;
    @NotNull
    @Size(min = 2)
    private String firstName;
    @NotNull
    @Size(min = 2)
    private String lastName;
    @NotNull
    @Size(min = 8)
    private String password;
    @NotNull
    @Size(min = 8)
    private String confirmPassword;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String country;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String city;
    @NotBlank
    private String street;

    public UserRegistrationBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegistrationBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserRegistrationBindingModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public UserRegistrationBindingModel setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserRegistrationBindingModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public UserRegistrationBindingModel setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public boolean passwordsMatch(){
        return this.getPassword().equals(this.getConfirmPassword());
    }
}
