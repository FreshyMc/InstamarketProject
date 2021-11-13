package com.example.instamarket.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProfileNamesBindingModel {
    @NotBlank
    @Size(min = 2)
    private String firstName;
    @NotBlank
    @Size(min = 2)
    private String lastName;

    public ProfileNamesBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public ProfileNamesBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ProfileNamesBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
